package com.skillnest.jobSeekerService.dtos.response;

import com.skillnest.jobSeekerService.data.model.JobSeeker;
import com.skillnest.jobSeekerService.data.model.VerificationDocument;
import lombok.Data;

import java.util.List;

@Data
public class VerificationDocumentResponse {
    private String message;
    private VerificationDocument document;
}
