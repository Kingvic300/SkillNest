package com.skillnest.jobSeekerService.service;

import com.skillnest.jobSeekerService.dtos.request.*;
import com.skillnest.jobSeekerService.dtos.response.*;

import java.util.List;

public interface JobSeekerService {
    RegisterJobSeekerResponse completeProfile(RegisterJobSeekerRequest registerJobSeekerRequest);

    UpdateJobSeekerProfileResponse updateProfile(UpdateJobSeekerProfileRequest request);

    UpdateJobSeekerProfileResponse getProfile(String id);

    UploadDocumentsResponse uploadDocuments(VerificationDocumentRequest documents);

    AvailabilitySlotResponse setAvailability(List<AvailabilitySlotRequest> slots);

    VerificationDocumentResponse getDocuments(String jobSeekerId);

    List<AvailabilitySlotResponse> getAvailability(String jobSeekerId);


}
