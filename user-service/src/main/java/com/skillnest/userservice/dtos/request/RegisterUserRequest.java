package com.skillnest.userservice.dtos.request;

import com.skillnest.userservice.data.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserRequest {
    private String email;
    private String otp;
    private Role role;
}
