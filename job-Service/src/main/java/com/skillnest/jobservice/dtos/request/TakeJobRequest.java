package com.skillnest.jobservice.dtos.request;

import lombok.Data;

@Data
public class TakeJobRequest {
    private String jobId;
    private String jobSeekerId;
}
