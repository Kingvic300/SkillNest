package com.skillnest.userservice.service;

import com.cloudinary.Cloudinary;
import com.skillnest.userservice.data.model.Email;
import com.skillnest.userservice.data.model.OTP;
import com.skillnest.userservice.data.model.User;
import com.skillnest.userservice.data.repositories.EmailRepository;
import com.skillnest.userservice.data.repositories.OTPRepository;
import com.skillnest.userservice.data.repositories.UserRepository;
import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.request.CreateUserRequest;
import com.skillnest.userservice.dtos.response.*;
import com.skillnest.userservice.exception.*;
import com.skillnest.userservice.mapper.UserMapper;
import com.skillnest.userservice.util.JwtUtil;
import com.skillnest.userservice.util.OTPGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final EmailRepository emailRepository;
    private final JwtUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final OTPRepository otpRepository;
    private final Cloudinary cloudinary;


    @Override
    public OTPResponse sendVerificationOTP(CreateUserRequest request) {
        log.error(request.getEmail());
        validateRegisterRequest(request);
        log.error("passed here 1");
        validateEmail(request.getEmail());
        log.error("passed here 2");

        String otp = OTPGenerator.generateOtp();
        log.error("passed here 4");
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(2);
        log.error("passed here 5");
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        log.error("passed here 6");

        Email email = new Email();
        email.setEmail(request.getEmail());
        email.setPassword(encodedPassword);
        email.setOtp(otp);
        email.setExpirationDate(expirationTime);
        emailRepository.save(email);

        log.error("passed here 9");
        emailService.sendEmail(request.getEmail(), otp);

        log.error("passed here 10");
        return UserMapper.mapToOtpSentResponse("OTP sent successfully. Please verify to complete registration.", request.getEmail());
    }

    @Override
    public CreatedUserResponse register(RegisterUserRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (request.getOtp() == null || request.getOtp().isEmpty()) {
            throw new IllegalArgumentException("OTP cannot be empty");
        }

        Email tempUserData = emailRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("No pending registration found for this email"));

        if (!tempUserData.getOtp().equals(request.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP code");
        }

        if (tempUserData.isUsed()) {
            throw new IllegalStateException("This OTP has already been used");
        }

        if (tempUserData.getExpirationDate().isBefore(LocalDateTime.now())) {
            emailRepository.deleteByEmail(request.getEmail());
            throw new IllegalStateException("OTP expired. Please request a new one");
        }

        User user = UserMapper.mapToUser(request);
        user.setEmail(tempUserData.getEmail());
        user.setPassword(tempUserData.getPassword());
        User saved = userRepository.save(user);

        tempUserData.setUsed(true);
        emailRepository.save(tempUserData);
        emailRepository.deleteByEmail(request.getEmail());

        return UserMapper.mapToCreatedUserResponse( saved,"Registration Successful");
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
            var jwtToken = jwtTokenUtil.generateToken(user);

            return UserMapper.mapToLoginResponse(jwtToken, "Login was successful", user);
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
        var token = jwtTokenUtil.generateToken(user);
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
    //    @Override
//    public LoginResponse handleGoogleLogin(String email, String name) {
//        if (email == null || email.isEmpty()) {
//            throw new IllegalArgumentException("Email cannot be empty");
//        }
//        if (name == null || name.isEmpty()) {
//            throw new IllegalArgumentException("Name cannot be empty");
//        }
//
//        Optional<User> userOpt = userRepository.findByEmail(email);
//        User user;
//        if (userOpt.isPresent()) {
//            user = userOpt.get();
//            if (!user.isGoogleUser()) {
//                throw new IllegalStateException("Email is registered with a password-based account. Please use password login.");
//            }
//        } else {
//            user = new User();
//            user.setEmail(email);
//            user.setName(name);
//            user.setGoogleUser(true);
//            user.setContactIds(new ArrayList<>());
//            user = userRepository.save(user);
//        }
//
//        String token = jwtTokenUtil.generateToken(user.getEmail());
//        return new LoginResponse("Google login successful", token, user.getId());
//    }
    private void validateEmail(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            throw new AlreadyExistsException("Email already registered");
        }
    }
    private void validateRegisterRequest(CreateUserRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }
}
