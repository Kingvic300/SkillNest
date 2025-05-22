package com.skillnest.userservice.service;

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
import com.skillnest.userservice.util.OTPGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class UserServiceImplTest {

        @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private EmailRepository emailRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private AuthenticationManager authenticationManager;
        @Autowired
        private OTPRepository otpRepository;



        private CreateUserRequest createUserRequest;
        private RegisterUserRequest registerUserRequest;

        @BeforeEach
        public void setUp() {
            userRepository.deleteAll();
            emailRepository.deleteAll();

            createUserRequest = new CreateUserRequest();
            createUserRequest.setEmail("oladimejivictor611@gmail.com");
            createUserRequest.setPassword("password123");

            registerUserRequest = new RegisterUserRequest();
            registerUserRequest.setEmail("oladimejivictor611@gmail.com");
            registerUserRequest.setUsername("username");
        }

        @AfterEach
        public void tearDown() {
            userRepository.deleteAll();
            emailRepository.deleteAll();
        }

        @Test
        public void testSendVerificationOTP_Success() {
            OTPResponse response = userService.sendVerificationOTP(createUserRequest);

            assertNotNull(response);
            assertEquals("OTP sent successfully. Please verify to complete registration.", response.getMessage());
            assertEquals(createUserRequest.getEmail(), response.getEmail());

            Optional<Email> savedEmail = emailRepository.findByEmail(createUserRequest.getEmail());
            assertTrue(savedEmail.isPresent());
            assertNotNull(savedEmail.get().getOtp());
            assertFalse(savedEmail.get().isUsed());
        }

        @Test
        public void testRegister_Success() {
            log.info("test");
            userService.sendVerificationOTP(createUserRequest);

            Email emailData = emailRepository.findByEmail(createUserRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Email not found"));
            registerUserRequest.setOtp(emailData.getOtp());

            CreatedUserResponse response = userService.register(registerUserRequest);

            assertNotNull(response);
            assertEquals("Registration Successful", response.getMessage());
            assertNotNull(response.getUser());

            Optional<User> savedUser = userRepository.findByEmail(createUserRequest.getEmail());
            assertTrue(savedUser.isPresent());
            assertEquals(createUserRequest.getEmail(), savedUser.get().getEmail());

            Optional<Email> usedEmail = emailRepository.findByEmail(createUserRequest.getEmail());
            assertFalse(usedEmail.isPresent());
        }

        @Test
        public void testRegister_InvalidOtp_ThrowsException() {
            userService.sendVerificationOTP(createUserRequest);

            registerUserRequest.setOtp("wrong-otp");

            assertThrows(IllegalArgumentException.class, () -> userService.register(registerUserRequest));
        }

        @Test
        public void testRegister_ExpiredOtp_ThrowsException() {
            Email email = new Email();
            email.setEmail(createUserRequest.getEmail());
            email.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
            email.setOtp(OTPGenerator.generateOtp());
            email.setExpirationDate(LocalDateTime.now().minusMinutes(5));
            emailRepository.save(email);

            registerUserRequest.setOtp(email.getOtp());

            assertThrows(IllegalStateException.class, () -> userService.register(registerUserRequest));

            assertFalse(emailRepository.findByEmail(createUserRequest.getEmail()).isPresent());
        }

        @Test
        public void testRegister_AlreadyUsedOtp_ThrowsException() {
            log.error("got here");
            userService.sendVerificationOTP(createUserRequest);
            log.error("passed here");

            Email email = emailRepository.findByEmail(createUserRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Email not found"));
            email.setUsed(true);
            emailRepository.save(email);

            registerUserRequest.setOtp(email.getOtp());

            assertThrows(IllegalStateException.class, () -> userService.register(registerUserRequest));
        }

        @Test
        public void testSendVerificationOTP_EmailAlreadyRegistered_ThrowsException() {
            userService.sendVerificationOTP(createUserRequest);
            Email email = emailRepository.findByEmail(createUserRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Email not found"));
            registerUserRequest.setOtp(email.getOtp());
            userService.register(registerUserRequest);

            assertThrows(AlreadyExistsException.class, () -> userService.sendVerificationOTP(createUserRequest));
        }
//    @Test
//    public void testUploadFile_Success() throws IOException {
//        MultipartFile file = new MockMultipartFile(
//                "test.jpg",
//                "test.jpg",
//                "image/jpeg",
//                "test image content".getBytes()
//        );
//
//        UploadResponse response = userService.uploadFile(file);
//
//        assertNotNull(response);
//        assertEquals("Image has been uploaded successfully", response.getMessage());
//        assertTrue(response.getCloudinaryUrl().contains("cloudinary"));
//    }
//
//    @Test
//    public void testUploadFile_EmptyFile_ThrowsException() {
//        MultipartFile emptyFile = new MockMultipartFile(
//                "empty.jpg",
//                "empty.jpg",
//                "image/jpeg",
//                new byte[0]
//        );
//
//        assertThrows(RuntimeException.class, () -> {
//            userService.uploadFile(emptyFile);
//        });
//    }

    @Test
    public void testLogin_Success() {
        userService.sendVerificationOTP(createUserRequest);
        Email email = emailRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));
        registerUserRequest.setOtp(email.getOtp());
        userService.register(registerUserRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerUserRequest.getUsername());
        loginRequest.setPassword(createUserRequest.getPassword());

        LoginResponse response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals("Login was successful", response.getMessage());
        assertNotNull(response.getToken());
    }

    @Test
    public void testLogin_InvalidPassword_ThrowsException() {
        userService.sendVerificationOTP(createUserRequest);
        Email email = emailRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));
        registerUserRequest.setOtp(email.getOtp());
        userService.register(registerUserRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerUserRequest.getUsername());
        loginRequest.setPassword("wrongpassword");

        assertThrows(InvalidPasswordException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void testLogin_UserNotFound_ThrowsException() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("nonexistentuser");
        loginRequest.setPassword("password");

        assertThrows(InvalidPasswordException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void testUpdateProfile_Success() {
        userService.sendVerificationOTP(createUserRequest);
        Email email = emailRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));
        registerUserRequest.setOtp(email.getOtp());
        userService.register(registerUserRequest);

        authenticateUser(registerUserRequest.getUsername(), createUserRequest.getPassword());

        UpdateUserProfileRequest updateRequest = new UpdateUserProfileRequest();
        updateRequest.setUsername("updateduser");
        updateRequest.setPhoneNumber("1234567890");
        updateRequest.setLocation("New Location");
        updateRequest.setProfilePicturePath("new/path.jpg");
        updateRequest.setActive(true);

        UpdateUserProfileResponse response = userService.updateProfile(updateRequest);

        assertNotNull(response);
        assertEquals("User profile updated successfully", response.getMessage());
        assertNotNull(response.getToken());

        User updatedUser = userRepository.findByUsername("updateduser")
                .orElseThrow(() -> new RuntimeException("User not found"));
        assertEquals("1234567890", updatedUser.getPhoneNumber());
        assertEquals("New Location", updatedUser.getLocation());
    }

    @Test
    public void testUpdateProfile_NotAuthenticated_ThrowsException() {
        UpdateUserProfileRequest updateRequest = new UpdateUserProfileRequest();
        updateRequest.setUsername("updateduser");

        SecurityContextHolder.clearContext();

        assertThrows(RuntimeException.class, () -> userService.updateProfile(updateRequest));
    }

    @Test
    public void testUpdateProfile_UserInactive_ThrowsException() {
        userService.sendVerificationOTP(createUserRequest);
        Email email = emailRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));
        registerUserRequest.setOtp(email.getOtp());
        User user = userService.register(registerUserRequest).getUser();
        user.setActive(false);
        userRepository.save(user);

        authenticateUser(registerUserRequest.getUsername(), createUserRequest.getPassword());

        UpdateUserProfileRequest updateRequest = new UpdateUserProfileRequest();
        updateRequest.setUsername("updateduser");

        assertThrows(IsNotActiveException.class, () -> userService.updateProfile(updateRequest));
    }

    @Test
    public void testResetPassword_Success() {
        userService.sendVerificationOTP(createUserRequest);
        Email email = emailRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));
        registerUserRequest.setOtp(email.getOtp());
        userService.register(registerUserRequest);

        ResetPasswordRequest resetRequest = new ResetPasswordRequest();
        resetRequest.setEmail(createUserRequest.getEmail());
        userService.sendResetOtp(resetRequest);

        OTP otp = otpRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        ChangePasswordRequest changeRequest = new ChangePasswordRequest();
        changeRequest.setEmail(createUserRequest.getEmail());
        changeRequest.setOtp(otp.getOtp());
        changeRequest.setNewPassword("newpassword123");

        ResetPasswordResponse response = userService.resetPassword(changeRequest);

        assertNotNull(response);
        assertEquals("Password reset successful", response.getMessage());

        User user = userRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        assertTrue(passwordEncoder.matches("newpassword123", user.getPassword()));
    }

    @Test
    public void testResetPassword_InvalidOtp_ThrowsException() {
        userService.sendVerificationOTP(createUserRequest);
        Email email = emailRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));
        registerUserRequest.setOtp(email.getOtp());
        userService.register(registerUserRequest);

        ChangePasswordRequest changeRequest = new ChangePasswordRequest();
        changeRequest.setEmail(createUserRequest.getEmail());
        changeRequest.setOtp("invalidotp");
        changeRequest.setNewPassword("newpassword123");

        assertThrows(InvalidOtpException.class, () -> {
            userService.resetPassword(changeRequest);
        });
    }

    @Test
    public void testResetPassword_ExpiredOtp_ThrowsException() {
        userService.sendVerificationOTP(createUserRequest);
        Email email = emailRepository.findByEmail(createUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));
        registerUserRequest.setOtp(email.getOtp());
        userService.register(registerUserRequest);

        OTP expiredOtp = new OTP();
        expiredOtp.setEmail(createUserRequest.getEmail());
        expiredOtp.setOtp(OTPGenerator.generateOtp());
        expiredOtp.setExpiresAt(LocalDateTime.now().minusMinutes(1));
        otpRepository.save(expiredOtp);

        ChangePasswordRequest changeRequest = new ChangePasswordRequest();
        changeRequest.setEmail(createUserRequest.getEmail());
        changeRequest.setOtp(expiredOtp.getOtp());
        changeRequest.setNewPassword("newpassword123");

        assertThrows(OtpExpiredException.class, () -> {
            userService.resetPassword(changeRequest);
        });
    }

    @Test
    public void testSendResetOtp_UserNotFound_ThrowsException() {
        ResetPasswordRequest resetRequest = new ResetPasswordRequest();
        resetRequest.setEmail("nonexistent@example.com");

        assertThrows(UserNotFoundException.class, () -> userService.sendResetOtp(resetRequest));
    }

    private void authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
