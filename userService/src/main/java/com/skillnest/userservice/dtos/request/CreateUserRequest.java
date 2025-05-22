package com.skillnest.userservice.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String password;
    private String email;
}
