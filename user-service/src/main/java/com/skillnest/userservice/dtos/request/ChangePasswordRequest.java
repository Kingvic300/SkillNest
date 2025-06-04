package com.skillnest.userservice.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordRequest {
    private String email;
    private String otp;
    private String newPassword;
}
