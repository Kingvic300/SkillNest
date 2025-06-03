package com.skillnest.userservice.service;

import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.request.CreateUserRequest;
import com.skillnest.userservice.dtos.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    OTPResponse sendVerificationOTP(CreateUserRequest request);
    CreatedUserResponse register(RegisterUserRequest request);
    UploadResponse uploadFile(MultipartFile file) throws IOException;
    LoginResponse login(LoginRequest loginResponse);
    UpdateUserProfileResponse updateProfile(UpdateUserProfileRequest updateUserProfileRequest);
    ResetPasswordResponse resetPassword(ChangePasswordRequest changePasswordRequest);
    ResetPasswordResponse sendResetOtp(ResetPasswordRequest resetPasswordRequest);
    LoginResponse handleGoogleLogin(String email, String name);

    FoundResponse findUserById(String id);
}

