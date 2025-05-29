package com.skillnest.jobSeekerService.service;

import com.skillnest.jobSeekerService.dtos.request.*;
import com.skillnest.jobSeekerService.dtos.response.*;

import java.util.List;

public interface JobSeekerService {
    RegisterJobSeekerResponse completeProfile(RegisterJobSeekerRequest registerJobSeekerRequest);

    UpdateJobSeekerProfileResponse updateProfile(String id, UpdateJobSeekerProfileRequest request);

    UpdateJobSeekerProfileResponse getProfile(String id);

    UploadDocumentsResponse uploadDocuments(String jobSeekerId, List<VerificationDocumentRequest> documents);

    WorkImageResponse uploadWorkImages(String jobSeekerId, List<WorkImageRequest> images);

    AvailabilitySlotResponse setAvailability(String jobSeekerId, List<AvailabilitySlotRequest> slots);

    BankAccountResponse setBankAccount(String jobSeekerId, BankAccountRequest request);

    List<VerificationDocumentResponse> getDocuments(String jobSeekerId);

    List<WorkImageResponse> getWorkImages(String  jobSeekerId);

    List<AvailabilitySlotResponse> getAvailability(String jobSeekerId);

    BankAccountResponse getBankAccount(String jobSeekerId);
}
