package com.skillnest.userservice.mapper;

import com.skillnest.userservice.data.enums.Role;
import com.skillnest.userservice.data.model.User;
import com.skillnest.userservice.dtos.request.CreateUserRequest;
import com.skillnest.userservice.dtos.request.UpdateUserProfileRequest;
import com.skillnest.userservice.dtos.response.CreatedUserResponse;
import com.skillnest.userservice.dtos.response.LoginResponse;
import com.skillnest.userservice.dtos.response.ResetPasswordResponse;
import com.skillnest.userservice.dtos.response.UpdateUserProfileResponse;
import com.skillnest.userservice.util.EmailVerification;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserMapper {
    public static User mapToUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(createUserRequest.getPassword());
        user.setEmail(EmailVerification.emailVerification(createUserRequest.getEmail()));
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        user.setRole(Role.USER);
        user.setLocation(createUserRequest.getLocation());
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicturePath(createUserRequest.getProfilePicturePath());
        user.setActive(true);
        return user;
    }
    public static void mapToUpdateProfile(UpdateUserProfileRequest updateUserProfileRequest, User user) {
        user.setUsername(updateUserProfileRequest.getUsername());
        user.setEmail(updateUserProfileRequest.getEmail());
        user.setPhoneNumber(updateUserProfileRequest.getPhoneNumber());
        user.setRole(Role.USER);
        user.setLocation(updateUserProfileRequest.getLocation());
        user.setProfilePicturePath(updateUserProfileRequest.getProfilePicturePath());
        user.setActive(updateUserProfileRequest.isActive());
    }
    public static UpdateUserProfileResponse mapToUpdateUserProfileResponse(String token, String message) {
        UpdateUserProfileResponse updateUserProfileResponse = new UpdateUserProfileResponse();
        updateUserProfileResponse.setMessage(message);
        updateUserProfileResponse.setToken(token);
        return updateUserProfileResponse;
    }
    public static CreatedUserResponse mapToCreatedUserResponse(User user, String message) {
        CreatedUserResponse createdUserResponse = new CreatedUserResponse();
        createdUserResponse.setUser(user);
        createdUserResponse.setMessage(message);
        return createdUserResponse;
    }
    public static LoginResponse mapToLoginResponse(String jwtToken, String message) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setMessage(message);
        return loginResponse;
    }
    public static ResetPasswordResponse mapToResetPasswordResponse(String message, String otp){
        ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
        resetPasswordResponse.setMessage(message);
        resetPasswordResponse.setOtp(otp);
        return resetPasswordResponse;
    }
}
