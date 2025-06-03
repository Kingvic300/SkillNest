package com.skillnest.jobservice.dtos.request;

import com.skillnest.jobservice.data.enums.JobStatus;
import com.skillnest.jobservice.data.model.WorkImage;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateJobRequest {
    private String jobId;
    private String title;
    private String description;
    private String location;
    private WorkImage workImage;
    private List<String> requiredSkills;
    private BigDecimal proposedPayment;
    private String jobType;
    private LocalDateTime deadline;
    private String contactInfo;
    private JobStatus status;
    private LocalDateTime lastUpdatedDate;
}
