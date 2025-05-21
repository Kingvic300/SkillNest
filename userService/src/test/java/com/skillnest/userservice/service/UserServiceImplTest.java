package com.skillnest.userservice.service;

import com.skillnest.userservice.data.model.OTP;
import com.skillnest.userservice.data.repositories.OTPRepository;
import com.skillnest.userservice.data.repositories.UserRepository;
import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.response.*;
import com.skillnest.userservice.exception.InvalidOtpException;
import com.skillnest.userservice.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    CreateUserRequest createUserRequest = new CreateUserRequest();

    @BeforeEach
    public void setUp() {
        createUserRequest.setEmail("oladimejivictor611@gmail.com");
        createUserRequest.setActive(true);
        createUserRequest.setPassword("password");
        createUserRequest.setUsername("victoroladimeji");
        createUserRequest.setLocation("Sabo Yaba");
        createUserRequest.setPhoneNumber("08144782521");

        userService.sendEmailValidationOTP(createUserRequest.getEmail());

        OTP otp = otpRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP not found"));
        createUserRequest.setOtp(otp.getOtp());
    }

    @Test
    public void testThatUserCanBeRegistered() {
        CreatedUserResponse createdUserResponse = userService.register(createUserRequest);
        assertNotNull(createdUserResponse.getUser());
        assertEquals("User Created Successfully", createdUserResponse.getMessage());
    }

    @Test
    public void testThatUserCanLogin() {
        userService.register(createUserRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("victoroladimeji");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);

        assertNotNull(loginResponse.getToken());
        assertEquals("Login was successful", loginResponse.getMessage());
    }

    @Test
    public void testThatUserCanUpdateTheirProfile() {
        userService.register(createUserRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("victoroladimeji");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        authenticatedUser(loginRequest);

        UpdateUserProfileRequest updateUserProfileRequest = new UpdateUserProfileRequest();
        updateUserProfileRequest.setEmail("updated@example.com");
        updateUserProfileRequest.setActive(true);
        updateUserProfileRequest.setUsername("updateduser");
        updateUserProfileRequest.setLocation("Sabo Yaba");
        updateUserProfileRequest.setPhoneNumber("08144782521");

        UpdateUserProfileResponse updateUserProfileResponse = userService.updateProfile(updateUserProfileRequest);

        assertEquals("User profile updated successfully", updateUserProfileResponse.getMessage());
        assertNotNull(updateUserProfileResponse.getToken());
    }

    @Test
    public void testThatResetPasswordWorks() {
        userService.register(createUserRequest);

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("oladimejivictor611@gmail.com");
        userService.sendResetOtp(resetPasswordRequest);

        OTP otp = otpRepository.findByEmail("oladimejivictor611@gmail.com")
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setEmail("oladimejivictor611@gmail.com");
        changePasswordRequest.setOtp(otp.getOtp());
        changePasswordRequest.setNewPassword("password");

        ResetPasswordResponse resetPasswordResponse = userService.resetPassword(changePasswordRequest);
        assertEquals("Password reset successful", resetPasswordResponse.getMessage());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("victoroladimeji");
        loginRequest.setPassword("password");

        LoginResponse loginResponse = userService.login(loginRequest);
        assertNotNull(loginResponse.getToken());
    }

    private void authenticatedUser(LoginRequest loginRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @Test
    public void testRegisterWithExistingUsernameThrowsException() {
        CreateUserRequest duplicateUsernameRequest = new CreateUserRequest();
        duplicateUsernameRequest.setEmail("newemail@example.com");
        duplicateUsernameRequest.setUsername("victoroladimeji");
        duplicateUsernameRequest.setPassword("password");
        duplicateUsernameRequest.setLocation("Sabo Yaba");
        duplicateUsernameRequest.setPhoneNumber("08144782521");
        duplicateUsernameRequest.setOtp(createUserRequest.getOtp());
        duplicateUsernameRequest.setActive(true);

        assertThrows(InvalidOtpException.class, () -> {
            userService.register(duplicateUsernameRequest);
        });
    }

    @Test
    public void testLoginWithWrongPasswordThrowsException() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("victoroladimeji");
        loginRequest.setPassword("wrongpassword");

        assertThrows(RuntimeException.class, () -> {
            userService.login(loginRequest);
        });
    }

    @Test
    public void testUpdateProfileWithoutAuthenticationThrowsException() {
        UpdateUserProfileRequest updateUserProfileRequest = new UpdateUserProfileRequest();
        updateUserProfileRequest.setEmail("updated@example.com");
        updateUserProfileRequest.setUsername("updateduser");
        updateUserProfileRequest.setLocation("Sabo Yaba");
        updateUserProfileRequest.setPhoneNumber("08144782521");
        updateUserProfileRequest.setActive(true);

        SecurityContextHolder.clearContext();

        assertThrows(RuntimeException.class, () -> {
            userService.updateProfile(updateUserProfileRequest);
        });
    }

    @Test
    public void testResetPasswordWithInvalidOtpThrowsException() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setEmail("oladimejivictor611@gmail.com");
        changePasswordRequest.setOtp("invalidotp");
        changePasswordRequest.setNewPassword("password");

        assertThrows(InvalidOtpException.class, () -> {
            userService.resetPassword(changePasswordRequest);
        });
    }

    @Test
    public void testSendResetOtpForNonExistingEmailThrowsException() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("nonexistingemail@example.com");

        assertThrows(UserNotFoundException.class, () -> {
            userService.sendResetOtp(resetPasswordRequest);
        });
    }

    @Test
    public void testRegisterWithInvalidEmailFormatThrowsException() {
        CreateUserRequest invalidEmailRequest = new CreateUserRequest();
        invalidEmailRequest.setEmail("invalid-email-format");
        invalidEmailRequest.setUsername("newuser");
        invalidEmailRequest.setPassword("password");
        invalidEmailRequest.setLocation("Sabo Yaba");
        invalidEmailRequest.setPhoneNumber("08144782521");
        invalidEmailRequest.setOtp("123456");
        invalidEmailRequest.setActive(true);

        assertThrows(RuntimeException.class, () -> {
            userService.register(invalidEmailRequest);
        });
    }

    @Test
    public void testRegisterWithMissingFieldsThrowsException() {
        CreateUserRequest missingFieldsRequest = new CreateUserRequest();
        missingFieldsRequest.setPassword("password");
        missingFieldsRequest.setLocation("Sabo Yaba");
        missingFieldsRequest.setPhoneNumber("08144782521");
        missingFieldsRequest.setOtp("123456");
        missingFieldsRequest.setActive(true);

        assertThrows(RuntimeException.class, () -> {
            userService.register(missingFieldsRequest);
        });
    }

    @AfterEach
    void tearDown() {
        otpRepository.deleteAll();
        userRepository.deleteAll();
        SecurityContextHolder.clearContext();

    }
}
