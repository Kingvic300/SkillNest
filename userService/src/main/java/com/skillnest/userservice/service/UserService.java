package com.skillnest.userservice.service;

import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    CreatedUserResponse register(CreateUserRequest createUserRequest);

    String uploadFile(MultipartFile file) throws IOException;

    OTPResponse sendEmailValidationOTP(String email);
    LoginResponse login(LoginRequest loginResponse);
    UpdateUserProfileResponse updateProfile(UpdateUserProfileRequest updateUserProfileRequest);
    ResetPasswordResponse resetPassword(ChangePasswordRequest changePasswordRequest);
    ResetPasswordResponse sendResetOtp(ResetPasswordRequest resetPasswordRequest);
}
