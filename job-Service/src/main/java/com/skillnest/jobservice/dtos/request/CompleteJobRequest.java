package com.skillnest.jobservice.dtos.request;

import lombok.Data;

@Data
public class CompleteJobRequest {
    private String jobId;
    private String jobSeekerId;
    private boolean isJobSeeker;
}
