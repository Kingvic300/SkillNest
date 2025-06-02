package com.skillnest.jobservice.dtos.response;

import com.skillnest.jobservice.data.enums.JobStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobResponse {
    private String id;
    private String title;
    private String description;
    private Long employerId;
    private Long jobSeekerId;
    private String location;
    private List<String> requiredSkills;
    private BigDecimal proposedPayment;
    private BigDecimal negotiatedPayment;
    private String jobType;
    private LocalDateTime deadline;
    private String contactInfo;
    private JobStatus status;
    private LocalDateTime postedDate;
    private LocalDateTime lastUpdatedDate;
}
