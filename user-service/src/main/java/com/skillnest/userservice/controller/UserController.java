package com.skillnest.userservice.controller;

import com.skillnest.userservice.data.model.User;
import com.skillnest.userservice.data.repositories.UserRepository;
import com.skillnest.userservice.dtos.UserDto;
import com.skillnest.userservice.dtos.request.*;
import com.skillnest.userservice.dtos.request.CreateUserRequest;
import com.skillnest.userservice.dtos.response.*;
import com.skillnest.userservice.exception.*;
import com.skillnest.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody RegisterUserRequest createUserRequest) {
        try{
            return ResponseEntity.ok(userService.register(createUserRequest));
        }catch(UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (OtpExpiredException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (InvalidOtpException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
    @PostMapping("login-user")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        try{
            return ResponseEntity.ok(userService.login(loginRequest));
        }catch (InvalidPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @PostMapping("send-email-verification")
    public ResponseEntity<?> sendEmailValidationOTP(@RequestBody CreateUserRequest createUserRequest){
        try{
            return ResponseEntity.ok(userService.sendVerificationOTP(createUserRequest ));
        }catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
    @PostMapping("update-profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody UpdateUserProfileRequest updateUserProfileRequest){
        try{
            return ResponseEntity.ok(userService.updateProfile(updateUserProfileRequest));

        }catch (IsNotActiveException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @PostMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        try{
            return ResponseEntity.ok(userService.resetPassword(changePasswordRequest));
        }catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());}
        catch (OtpExpiredException e) {
            return ResponseEntity.badRequest().body(e.getMessage());}
        catch (InvalidOtpException e) {
            return ResponseEntity.badRequest().body(e.getMessage());}
        catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
    @PostMapping("send-reset-otp")
    public ResponseEntity<?> sendResetOTP(@RequestBody ResetPasswordRequest resetPasswordRequest){
        try{
            return ResponseEntity.ok(userService.sendResetOtp(resetPasswordRequest));
        }catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
    @PostMapping("/upload-picture")
    public ResponseEntity<UploadResponse> uploadPicture(@RequestParam("file") MultipartFile file) {
        try {
            UploadResponse response = userService.uploadFile(file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UploadResponse("Upload failed: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UploadResponse("Unexpected error: " + e.getMessage(), null));
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable String userId){
        FoundResponse userFound = userService.findUserById(userId);
        Optional<User> existingUser = userRepository.findById(userFound.getId());
        if(existingUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        User user = existingUser.get();
        UserDto dto = new UserDto(user.getId(), user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(dto);
    }
}
