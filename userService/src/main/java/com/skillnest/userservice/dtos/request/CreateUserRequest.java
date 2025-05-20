package com.skillnest.userservice.dtos.request;

import com.skillnest.userservice.data.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateUserRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String profilePicturePath;
    private String location;
    private boolean isActive;
}
