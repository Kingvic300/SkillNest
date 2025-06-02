package com.skillnest.jobSeekerService.dtos.request;

import lombok.Data;

@Data
public class UpdateJobSeekerProfileRequest {
    private String userId;
    private String fullName;
    private String resumeUrl;
    private String location;
    private String phoneNumber;
    private String profilePictureUrl;
}
