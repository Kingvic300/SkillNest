package com.skillnest.jobservice.dtos.request;

import com.skillnest.jobservice.data.enums.JobStatus;
import lombok.Data;

@Data
public class ChangeJobStatusRequest {
    private String jobId;
    private JobStatus status;
}
