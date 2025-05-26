package com.skillnest.userservice.controller;

import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.request.CreateUserRequest;
import com.skillnest.userservice.dtos.response.*;
import com.skillnest.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("create-user")
    public ResponseEntity<CreatedUserResponse> createUser(@RequestBody RegisterUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.register(createUserRequest));
    }
    @PostMapping("login-user")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }
    @PostMapping("send-email-verification")
    public ResponseEntity<OTPResponse> sendEmailValidationOTP(@RequestBody CreateUserRequest createUserRequest){
        return ResponseEntity.ok(userService.sendVerificationOTP(createUserRequest ));
    }
    @PostMapping("update-profile")
    public ResponseEntity<UpdateUserProfileResponse> updateUserProfile(@RequestBody UpdateUserProfileRequest updateUserProfileRequest){
        return ResponseEntity.ok(userService.updateProfile(updateUserProfileRequest));
    }
    @PostMapping("reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return ResponseEntity.ok(userService.resetPassword(changePasswordRequest));
    }
    @PostMapping("send-reset-otp")
    public ResponseEntity<ResetPasswordResponse> sendResetOTP(@RequestBody ResetPasswordRequest resetPasswordRequest){
        return ResponseEntity.ok(userService.sendResetOtp(resetPasswordRequest));
    }
    @PostMapping("upload-picture")
    public ResponseEntity<UploadResponse> uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(userService.uploadFile(file));
    }
}
