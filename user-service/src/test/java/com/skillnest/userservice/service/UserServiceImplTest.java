package com.skillnest.userservice.service;

import com.cloudinary.Cloudinary;
import com.skillnest.userservice.data.enums.Role;
import com.skillnest.userservice.data.model.PendingUser;
import com.skillnest.userservice.data.model.OTP;
import com.skillnest.userservice.data.model.User;
import com.skillnest.userservice.data.repositories.PendingUserRepository;
import com.skillnest.userservice.data.repositories.OTPRepository;
import com.skillnest.userservice.data.repositories.UserRepository;
import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.response.CreatedUserResponse;
import com.skillnest.userservice.dtos.response.LoginResponse;
import com.skillnest.userservice.dtos.response.OTPResponse;
import com.skillnest.userservice.dtos.response.ResetPasswordResponse;
import com.skillnest.userservice.exception.*;
import com.skillnest.userservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PendingUserRepository pendingUserRepository;

    @Mock
    private JwtUtil jwtTokenUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private OTPRepository otpRepository;

    @Mock
    private Cloudinary cloudinary;

    @InjectMocks
    private UserServiceImpl userService;

    private CreateUserRequest createUserRequest;
    private RegisterUserRequest registerUserRequest;
    private LoginRequest loginRequest;
    private ChangePasswordRequest changePasswordRequest;
    private ResetPasswordRequest resetPasswordRequest;
    private User user;
    private PendingUser pendingUser;
    private OTP otp;

    @BeforeEach
    void setUp() {
        createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@example.com");
        createUserRequest.setPassword("password123");
        createUserRequest.setRole(Role.ADMIN);

        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("test@example.com");
        registerUserRequest.setOtp("123456");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");
        loginRequest.setRole(Role.ADMIN.name());

        changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setEmail("test@example.com");
        changePasswordRequest.setOtp("123456");
        changePasswordRequest.setNewPassword("newPassword123");

        resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("test@example.com");

        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.ADMIN);

        pendingUser = new PendingUser();
        pendingUser.setEmail("test@example.com");
        pendingUser.setOtp("123456");
        pendingUser.setExpiryTime(LocalDateTime.now().plusMinutes(2));

        otp = new OTP();
        otp.setEmail("test@example.com");
        otp.setOtp("123456");
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(30));
    }

    @Test
    void sendVerificationOTP_ShouldThrowExceptionWhenEmailExists() {
        when(userRepository.findByEmail(createUserRequest.getEmail())).thenReturn(Optional.of(user));

        assertThrows(AlreadyExistsException.class, () -> userService.sendVerificationOTP(createUserRequest));
    }

    @Test
    void sendVerificationOTP_ShouldSavePendingUserAndSendEmail() {
        when(userRepository.findByEmail(createUserRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        OTPResponse response = userService.sendVerificationOTP(createUserRequest);

        verify(pendingUserRepository, times(1)).save(any(PendingUser.class));
        verify(emailService, times(1)).sendEmail(eq(createUserRequest.getEmail()), anyString());
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    void register_ShouldThrowExceptionWhenPendingUserNotFound() {
        when(pendingUserRepository.findByEmail(registerUserRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.register(registerUserRequest));
    }

    @Test
    void register_ShouldThrowExceptionWhenOtpInvalid() {
        when(pendingUserRepository.findByEmail(registerUserRequest.getEmail())).thenReturn(Optional.of(pendingUser));
        registerUserRequest.setOtp("wrongOtp");

        assertThrows(InvalidOtpException.class, () -> userService.register(registerUserRequest));
    }

    @Test
    void register_ShouldThrowExceptionWhenOtpExpired() {
        pendingUser.setExpiryTime(LocalDateTime.now().minusMinutes(5));
        when(pendingUserRepository.findByEmail(registerUserRequest.getEmail())).thenReturn(Optional.of(pendingUser));

        assertThrows(OtpExpiredException.class, () -> userService.register(registerUserRequest));
    }

    @Test
    void register_ShouldSaveUserAndDeletePendingUser() {
        when(pendingUserRepository.findByEmail(registerUserRequest.getEmail())).thenReturn(Optional.of(pendingUser));
        when(userRepository.save(any(User.class))).thenReturn(user);

        CreatedUserResponse response = userService.register(registerUserRequest);

        verify(userRepository, times(1)).save(any(User.class));
        verify(pendingUserRepository, times(1)).delete(pendingUser);
        assertEquals("test@example.com", response.getUser().getEmail());
    }

    @Test
    void login_ShouldThrowExceptionWhenAuthenticationFails() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> userService.login(loginRequest));
    }

    @Test
    void login_ShouldReturnTokenWhenSuccessful() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenUtil.generateToken(any(User.class))).thenReturn("jwtToken");

        LoginResponse response = userService.login(loginRequest);

        assertEquals("jwtToken", response.getToken());
    }

    @Test
    void sendResetOtp_ShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByEmail(resetPasswordRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.sendResetOtp(resetPasswordRequest));
    }

    @Test
    void sendResetOtp_ShouldSaveOtpAndSendEmail() {
        when(userRepository.findByEmail(resetPasswordRequest.getEmail())).thenReturn(Optional.of(user));

        ResetPasswordResponse response = userService.sendResetOtp(resetPasswordRequest);

        verify(otpRepository, times(1)).save(any(OTP.class));
        verify(emailService, times(1)).sendResetPasswordEmail(eq(resetPasswordRequest.getEmail()), anyString());
        assertNotNull(response.getOtp());
    }

    @Test
    void resetPassword_ShouldThrowExceptionWhenOtpInvalid() {
        when(otpRepository.findByEmailAndOtp(changePasswordRequest.getEmail(), changePasswordRequest.getOtp()))
                .thenReturn(Optional.empty());

        assertThrows(InvalidOtpException.class, () -> userService.resetPassword(changePasswordRequest));
    }

    @Test
    void resetPassword_ShouldUpdatePasswordAndDeleteOtp() {
        when(otpRepository.findByEmailAndOtp(changePasswordRequest.getEmail(), changePasswordRequest.getOtp()))
                .thenReturn(Optional.of(otp));
        when(userRepository.findByEmail(changePasswordRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(changePasswordRequest.getNewPassword())).thenReturn("newEncodedPassword");

        ResetPasswordResponse response = userService.resetPassword(changePasswordRequest);

        verify(userRepository, times(1)).save(user);
        verify(otpRepository, times(1)).delete(otp);
        assertEquals("123456", response.getOtp());
        assertEquals("newEncodedPassword", user.getPassword());
    }

    @Test
    void validateRegisterRequest_ShouldThrowExceptionWhenEmailEmpty() {
        CreateUserRequest invalidRequest = new CreateUserRequest();
        invalidRequest.setPassword("password");
        invalidRequest.setRole(Role.ADMIN);

        assertThrows(IllegalArgumentException.class, () -> userService.sendVerificationOTP(invalidRequest));
    }

    @Test
    void validateRegisterRequest_ShouldThrowExceptionWhenPasswordEmpty() {
        CreateUserRequest invalidRequest = new CreateUserRequest();
        invalidRequest.setEmail("test@example.com");
        invalidRequest.setRole(Role.ADMIN);

        assertThrows(IllegalArgumentException.class, () -> userService.sendVerificationOTP(invalidRequest));
    }
}