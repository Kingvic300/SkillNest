package com.skillnest.userservice.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OTPResponse {
    private String message;
    private String email;

}
