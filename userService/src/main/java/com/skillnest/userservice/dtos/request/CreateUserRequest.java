package com.skillnest.userservice.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String location;
    private boolean isActive;
}
