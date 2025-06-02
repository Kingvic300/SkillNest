package com.skillnest.jobSeekerService.dtos.request;

import com.skillnest.jobSeekerService.data.enums.VerificationStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VerificationDocumentRequest {
    private String jobSeekerId;
    private String type;
    private MultipartFile documentFile;
    private VerificationStatus status;
}
