package com.skillnest.userservice.dtos.response;

import com.skillnest.userservice.data.model.User;
import lombok.Data;

@Data
public class CreatedUserResponse {
    private User user;
    private String message;
    private String jwtToken;

}
