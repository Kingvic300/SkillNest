package com.skillnest.userservice.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserProfileResponse {
    private String token;
    private String message;
}
