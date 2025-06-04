package com.skillnest.userservice.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OTPRequest {
    private String otp;
    private String email;
}
