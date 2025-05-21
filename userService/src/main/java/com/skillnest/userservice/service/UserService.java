package com.skillnest.userservice.service;

import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.response.*;

public interface UserService {
    CreatedUserResponse register(CreateUserRequest createUserRequest);
    void sendEmailValidationOTP(String email);
    LoginResponse login(LoginRequest loginResponse);
    UpdateUserProfileResponse updateProfile(UpdateUserProfileRequest updateUserProfileRequest);
    ResetPasswordResponse resetPassword(ChangePasswordRequest changePasswordRequest);
    void sendResetOtp(ResetPasswordRequest resetPasswordRequest);
}
