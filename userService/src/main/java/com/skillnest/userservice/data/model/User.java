package com.skillnest.userservice.data.model;


import com.skillnest.userservice.data.enums.Role;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private String profilePicturePath;
    private LocalDateTime registrationDate;
    private String location;
    private boolean isActive;
    private Role role;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
