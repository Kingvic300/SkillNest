package com.skillnest.userservice.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordResponse {
    private String message;
    private String otp;

}
