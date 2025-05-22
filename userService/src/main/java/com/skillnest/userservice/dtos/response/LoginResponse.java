package com.skillnest.userservice.dtos.response;

import com.skillnest.userservice.data.model.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private User user;
    private String token;
    private String message;
}
