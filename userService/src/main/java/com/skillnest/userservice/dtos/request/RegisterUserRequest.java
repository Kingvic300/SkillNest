package com.skillnest.userservice.dtos.request;

import com.skillnest.userservice.data.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RegisterUserRequest {
    private String email;
    private String otp;
    private String fullName;
    private String username;
    private String phoneNumber;
    private String location;
    private boolean isActive;
    private boolean verified = false;
    private Role role;
}
