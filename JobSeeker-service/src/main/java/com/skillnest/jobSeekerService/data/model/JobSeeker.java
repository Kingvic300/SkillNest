package com.skillnest.jobSeekerService.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("job_seeker")
public class JobSeeker {
    @Id
    private String id;
    private String location;
    private String resumeUrl;
    private String userId;
    private String fullName;
    private String phoneNumber;
    private String profilePictureUrl;
    private String bio;
    private boolean isVerified;
    private float rating;
    private List<String> skillIds;
    private List<String> workImageIds;
    private String availabilitySlotIds;
    private String bankAccountId;
    private String documentIds;
}
