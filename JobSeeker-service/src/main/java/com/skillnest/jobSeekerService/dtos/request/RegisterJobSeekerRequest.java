package com.skillnest.jobSeekerService.dtos.request;

import com.skillnest.jobSeekerService.data.model.AvailabilitySlot;
import com.skillnest.jobSeekerService.data.model.BankAccount;
import com.skillnest.jobSeekerService.data.model.VerificationDocument;
import com.skillnest.jobSeekerService.data.model.WorkImage;
import lombok.Data;

import java.util.List;

@Data
public class RegisterJobSeekerRequest {
    private String userId;
    private String fullName;
    private String profilePictureUrl;
    private String bio;
    private List<String> skillIds;
    private List<WorkImage> workImages;
    private List<AvailabilitySlot> availabilitySlots;
    private BankAccount bankAccount;
    private List<VerificationDocument> documents;
}
