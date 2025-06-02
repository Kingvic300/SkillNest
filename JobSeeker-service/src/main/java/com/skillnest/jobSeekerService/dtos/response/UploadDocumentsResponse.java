package com.skillnest.jobSeekerService.dtos.response;

import com.skillnest.jobSeekerService.data.model.VerificationDocument;
import lombok.Data;

@Data
public class UploadDocumentsResponse {
    private String message;
    private VerificationDocument document;
}
