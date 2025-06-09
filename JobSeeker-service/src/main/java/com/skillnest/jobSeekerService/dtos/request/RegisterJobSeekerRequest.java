package com.skillnest.jobSeekerService.dtos.request;

import com.skillnest.jobSeekerService.data.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class RegisterJobSeekerRequest {
    private String email;
    private String userId;
    private String fullName;
    private String profilePictureUrl;
    private String bio;
    private List<String> skillIds;
    private List<String> workImageIds;
    private String availabilitySlotIds;
    private String bankAccountId;
    private String documentIds;
}

