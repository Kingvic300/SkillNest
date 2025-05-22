package com.skillnest.userservice.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class OTP {

    @Id
    private String id;
    private String otp;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean used = false;
}
