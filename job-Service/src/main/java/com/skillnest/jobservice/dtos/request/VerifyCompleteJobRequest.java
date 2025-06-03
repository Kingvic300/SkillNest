package com.skillnest.jobservice.dtos.request;

import lombok.Data;

@Data
public class VerifyCompleteJobRequest {
    private String jobId;
    private String userId;
    private boolean isJobSeeker;
}
