package com.skillnest.userservice.dtos.request;

import com.skillnest.userservice.data.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserProfileRequest {
    private String fullName;
    private String email;
    private String username;
    private String location;
    private String phoneNumber;
    private String profilePicturePath;
    private boolean isActive;
    private Role roles;
}
