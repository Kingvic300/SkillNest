package com.skillnest.userservice.service;

import com.cloudinary.Cloudinary;
import com.skillnest.userservice.data.model.OTP;
import com.skillnest.userservice.data.model.User;
import com.skillnest.userservice.data.repositories.OTPRepository;
import com.skillnest.userservice.data.repositories.UserRepository;
import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.response.*;
import com.skillnest.userservice.exception.*;
import com.skillnest.userservice.mapper.OTPMapper;
import com.skillnest.userservice.mapper.UserMapper;
import com.skillnest.userservice.util.JwtUtil;
import com.skillnest.userservice.util.OTPGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final OTPRepository otpRepository;
    private final EmailService emailService;
    private final Cloudinary cloudinary;


    @Override
    public CreatedUserResponse register(CreateUserRequest createUserRequest) {
        OTP otp = otpRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new InvalidOtpException("OTP not found for email"));

        if (!otp.getOtp().equals(createUserRequest.getOtp())) {
            throw new InvalidOtpException("Invalid OTP");
        }
        if (otp.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new OtpExpiredException("OTP has expired");
        }
        if(userRepository.existsByUsername(createUserRequest.getUsername())){
            throw new AlreadyExistsException("Username already exists");
        }

        if(userRepository.existsByEmail(createUserRequest.getEmail())){
            throw new AlreadyExistsException("Email already exists");
        }
        User user = UserMapper.mapToUser(createUserRequest);
        String encryptedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        otpRepository.delete(otp);
        return UserMapper.mapToCreatedUserResponse(user, "User Created Successfully");
    }

    @Override
    public UploadResponse uploadFile(MultipartFile file) throws IOException {
        var cloud = cloudinary
                .uploader()
                .upload(file.getBytes(), Map.of("public_id",UUID.randomUUID().toString()))
                .get("url")
                .toString();
        return UserMapper.mapToUploadResponse("Image has been uploaded successfully", cloud);
    }

    @Override
    public OTPResponse sendEmailValidationOTP(String email) {
        if (otpRepository.existsByEmail(email)) {
            otpRepository.deleteByEmail(email);
        }
        OTP otp = OTPMapper.mapToOTP(email);
        otpRepository.save(otp);
        emailService.sendEmail(email, otp.getOtp());
        return OTPMapper.mapToOTPResponse("OTP sent successfully", otp.getId());

    }
    @Override
    public LoginResponse login(LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));
            Optional<User> existingUser = userRepository.findByUsername(loginRequest.getUsername());
            if (existingUser.isEmpty()) {
                throw new UserNotFoundException("User not found with username");
            }
            User user = existingUser.get();
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new InvalidPasswordException("Invalid password");
            }
            var jwtToken = jwtUtil.generateToken(user);

            return UserMapper.mapToLoginResponse(jwtToken, "Login was successful");
        }catch (BadCredentialsException e){
           throw new InvalidPasswordException(e.getMessage());
        }
    }

    @Override
    public UpdateUserProfileResponse updateProfile(UpdateUserProfileRequest updateUserProfileRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        String username = authentication.getName();
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User not found with username");
        }
        User user = existingUser.get();

        if (!user.isActive()) {
            throw new IsNotActiveException("Account has been deactivated");
        }
        UserMapper.mapToUpdateProfile(updateUserProfileRequest, user);
        userRepository.save(user);
        var token = jwtUtil.generateToken(user);
        return UserMapper.mapToUpdateUserProfileResponse(token, "User profile updated successfully");
    }

    @Override
    public ResetPasswordResponse resetPassword(ChangePasswordRequest changePasswordRequest) {
        Optional<OTP> optionalOtp = otpRepository.findByEmailAndOtp(changePasswordRequest.getEmail(), changePasswordRequest.getOtp());

        if (optionalOtp.isEmpty()) {
            throw new InvalidOtpException("Invalid OTP");
        }
        OTP otp = optionalOtp.get();
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            otpRepository.delete(otp);
            throw new OtpExpiredException("OTP has expired");
        }
        User user = userRepository.findByEmail(changePasswordRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String encodedPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        otpRepository.delete(otp);
        return UserMapper.mapToResetPasswordResponse("Password reset successful", otp.getOtp());
    }
    @Override
    public ResetPasswordResponse sendResetOtp(ResetPasswordRequest resetPasswordRequest){
        Optional<User> user = userRepository.findByEmail(resetPasswordRequest.getEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        OTP otp = new OTP();
        otp.setId(UUID.randomUUID().toString());
        otp.setOtp(OTPGenerator.generateOtp());
        otp.setEmail(resetPasswordRequest.getEmail());
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(30));
        otpRepository.save(otp);
        emailService.sendResetPasswordEmail(resetPasswordRequest.getEmail(), otp.getOtp());
        return UserMapper.mapToResetPasswordResponse("Email sent Successfully", otp.getOtp());
    }
}
