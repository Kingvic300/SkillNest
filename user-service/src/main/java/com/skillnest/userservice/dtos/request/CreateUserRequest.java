package com.skillnest.userservice.dtos.request;

import com.skillnest.userservice.data.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String password;
    private String email;
    private Role role;
}
