package com.skillnest.userservice.data.model;

import com.skillnest.userservice.data.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Setter
@Getter
public class PendingUser {
    @Id
    private String id;
    private String email;
    private String password;
    private String otp;
    private LocalDateTime expiryTime;
    private Role role;
}
