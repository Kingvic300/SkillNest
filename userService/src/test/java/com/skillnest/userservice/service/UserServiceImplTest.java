package com.skillnest.userservice.service;

import com.skillnest.userservice.data.repositories.UserRepository;
import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.response.CreatedUserResponse;
import com.skillnest.userservice.dtos.response.LoginResponse;
import com.skillnest.userservice.dtos.response.ResetPasswordResponse;
import com.skillnest.userservice.dtos.response.UpdateUserProfileResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;


    @Test
    public void testThatUserCanBeRegistered(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("oladimejivictor611@gmail.com");
        createUserRequest.setActive(true);
        createUserRequest.setPassword("password");
        createUserRequest.setUsername("VictorOladimeji");
        createUserRequest.setLocation("Sabo yaba");
        createUserRequest.setPhoneNumber("08144782521");
        createUserRequest.setProfilePicturePath("not yet added");
        CreatedUserResponse createdUserResponse = userService.register(createUserRequest);
        assertNotNull(createdUserResponse.getJwtToken());
        assertEquals("User Created Successfully", createdUserResponse.getMessage());
    }
    @Test
    public void testThatUserCanLogin(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("oladimejivictor611@gmail.com");
        createUserRequest.setActive(true);
        createUserRequest.setPassword("password");
        createUserRequest.setUsername("VictorOladimeji");
        createUserRequest.setLocation("Sabo yaba");
        createUserRequest.setPhoneNumber("08144782521");
        createUserRequest.setProfilePicturePath("not yet added");
        CreatedUserResponse createdUserResponse = userService.register(createUserRequest);
        assertNotNull(createdUserResponse.getJwtToken());
        assertEquals("User Created Successfully", createdUserResponse.getMessage());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("VictorOladimeji");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertNotNull(loginResponse.getToken());
        assertEquals("Login was successful", loginResponse.getMessage());

    }

    @Test
    public void testThatStudentCanUpdateTheirProfile(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("oladimejivictor611@gmail.com");
        createUserRequest.setActive(true);
        createUserRequest.setPassword("password");
        createUserRequest.setUsername("VictorOladimeji");
        createUserRequest.setLocation("Sabo yaba");
        createUserRequest.setPhoneNumber("08144782521");
        createUserRequest.setProfilePicturePath("not yet added");
        CreatedUserResponse createdUserResponse = userService.register(createUserRequest);
        assertNotNull(createdUserResponse.getJwtToken());
        assertEquals("User Created Successfully", createdUserResponse.getMessage());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("VictorOladimeji");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertNotNull(loginResponse.getToken());
        assertEquals("Login was successful", loginResponse.getMessage());
        authenticatedUser(loginRequest);

        UpdateUserProfileRequest updateUserProfileRequest = new UpdateUserProfileRequest();
        updateUserProfileRequest.setEmail("oladimejivictor611@gmail.com");
        updateUserProfileRequest.setActive(true);
        updateUserProfileRequest.setUsername("Oladimeji Victor");
        updateUserProfileRequest.setLocation("Sabo,yaba");
        updateUserProfileRequest.setPhoneNumber("08144782521");
        updateUserProfileRequest.setProfilePicturePath("not yet added");
        UpdateUserProfileResponse updateUserProfileResponse = userService.updateProfile(updateUserProfileRequest);
        assertEquals("User profile updated successfully", updateUserProfileResponse.getMessage());
        assertNotNull(updateUserProfileResponse.getToken());
    }
    @Test
    public void testThatResetPasswordWorks() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("oladimejivictor611@gmail.com");
        createUserRequest.setActive(true);
        createUserRequest.setPassword("password");
        createUserRequest.setUsername("VictorOladimeji");
        createUserRequest.setLocation("Sabo yaba");
        createUserRequest.setPhoneNumber("08144782521");
        createUserRequest.setProfilePicturePath("not yet added");
        userService.register(createUserRequest);

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("oladimejivictor611@gmail.com");
        ResetPasswordResponse otpResponse = userService.sendResetOtp(resetPasswordRequest);
        assertNotNull(otpResponse.getOtp());
        assertEquals("Email sent Successfully", otpResponse.getMessage());

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setEmail("oladimejivictor611@gmail.com");
        changePasswordRequest.setOtp(otpResponse.getOtp());
        changePasswordRequest.setNewPassword("newSecurePassword123");

        ResetPasswordResponse resetPasswordResponse = userService.resetPassword(changePasswordRequest);
        assertEquals("Password reset successful", resetPasswordResponse.getMessage());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("VictorOladimeji");
        loginRequest.setPassword("newSecurePassword123");

        LoginResponse loginResponse = userService.login(loginRequest);
        assertNotNull(loginResponse.getToken());
        assertEquals("Login was successful", loginResponse.getMessage());
    }

    @Test
    public void testThatSendResetOTPIsSuccessful(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("oladimejivictor611@gmail.com");
        createUserRequest.setActive(true);
        createUserRequest.setPassword("password");
        createUserRequest.setUsername("VictorOladimeji");
        createUserRequest.setLocation("Sabo yaba");
        createUserRequest.setPhoneNumber("08144782521");
        createUserRequest.setProfilePicturePath("not yet added");
        CreatedUserResponse createdUserResponse = userService.register(createUserRequest);
        assertNotNull(createdUserResponse.getJwtToken());
        assertEquals("User Created Successfully", createdUserResponse.getMessage());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("VictorOladimeji");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertNotNull(loginResponse.getToken());
        assertEquals("Login was successful", loginResponse.getMessage());
        authenticatedUser(loginRequest);

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("oladimejivictor611@gmail.com");

        ResetPasswordResponse resetPasswordResponse = userService.sendResetOtp(resetPasswordRequest);
        assertNotNull(resetPasswordResponse.getOtp());
        assertEquals("Email sent Successfully", resetPasswordResponse.getMessage());

    }
    private void authenticatedUser(LoginRequest loginRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

}