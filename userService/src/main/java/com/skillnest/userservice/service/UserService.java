package com.skillnest.userservice.service;

import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.response.CreatedUserResponse;
import com.skillnest.userservice.dtos.response.LoginResponse;
import com.skillnest.userservice.dtos.response.ResetPasswordResponse;
import com.skillnest.userservice.dtos.response.UpdateUserProfileResponse;

public interface UserService {
    CreatedUserResponse register(CreateUserRequest createUserRequest);
    LoginResponse login(LoginRequest loginResponse);
    UpdateUserProfileResponse updateProfile(UpdateUserProfileRequest updateUserProfileRequest);
    ResetPasswordResponse resetPassword(ChangePasswordRequest changePasswordRequest);
    ResetPasswordResponse sendResetOtp(ResetPasswordRequest resetPasswordRequest);
}
