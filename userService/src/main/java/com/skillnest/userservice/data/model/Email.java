package com.skillnest.userservice.data.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Setter
@Getter
public class Email {
    @Id
    private String id;
    private boolean used;
    private LocalDateTime expirationDate;
    private String password;
    private String otp;
    private String email;
}
