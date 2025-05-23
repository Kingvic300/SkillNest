package com.skillnest.userservice.mapper;

import com.skillnest.userservice.data.enums.Role;
import com.skillnest.userservice.data.model.User;
import com.skillnest.userservice.dtos.request.CreateUserRequest;
import com.skillnest.userservice.dtos.request.RegisterUserRequest;
import com.skillnest.userservice.dtos.request.UpdateUserProfileRequest;
import com.skillnest.userservice.dtos.response.*;
import com.skillnest.userservice.util.EmailVerification;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserMapper {
    public static User mapToUser(RegisterUserRequest createUserRequest) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(createUserRequest.getUsername());
        user.setEmail(EmailVerification.emailVerification(createUserRequest.getEmail()));
        user.setRole(Role.USER);
        user.setRegistrationDate(LocalDateTime.now());
        user.setActive(true);
        user.setFullName(createUserRequest.getFullName());
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        user.setLocation(createUserRequest.getLocation());
        user.setVerified(true);
        return user;
    }
    public static void mapToUpdateProfile(UpdateUserProfileRequest updateUserProfileRequest, User user) {
        user.setUsername(updateUserProfileRequest.getUsername());
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
    public static LoginResponse mapToLoginResponse(String jwtToken, String message, User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setMessage(message);
        loginResponse.setUser(user);
        return loginResponse;
    }
    public static ResetPasswordResponse mapToResetPasswordResponse(String message, String otp){
        ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
        resetPasswordResponse.setMessage(message);
        resetPasswordResponse.setOtp(otp);
        return resetPasswordResponse;
    }
    public static UploadResponse mapToUploadResponse(String message, String cloudinaryUrl){
        UploadResponse uploadResponse = new UploadResponse();
        uploadResponse.setMessage(message);
        uploadResponse.setCloudinaryUrl(cloudinaryUrl);
        return uploadResponse;
    }

    public static OTPResponse mapToOtpSentResponse(String message, String email) {
        OTPResponse otpResponse = new OTPResponse();
        otpResponse.setMessage(message);
        otpResponse.setEmail(email);
        return otpResponse;
    }
}
