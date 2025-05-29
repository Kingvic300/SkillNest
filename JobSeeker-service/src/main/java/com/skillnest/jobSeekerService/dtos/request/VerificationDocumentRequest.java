package com.skillnest.jobSeekerService.dtos.request;

import com.skillnest.jobSeekerService.data.enums.VerificationStatus;
import lombok.Data;

@Data
public class VerificationDocumentRequest {
    private String jobSeekerId;
    private String type;
    private String documentUrl;
    private VerificationStatus status;
}
