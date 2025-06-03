package com.skillnest.jobservice.data.model;

import com.skillnest.jobservice.data.enums.JobStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("job")
@Builder
public class Job {
    @Id
    private String id;
    private String title;
    private String description;
    private String employerId;
    private String jobSeekerId;
    private String location;
    private WorkImage workImage;
    private List<String> requiredSkills;
    private BigDecimal proposedPayment;
    private BigDecimal negotiatedPayment;
    private String jobType;
    private LocalDateTime deadline;
    private String contactInfo;
    private JobStatus status;
    private LocalDateTime postedDate;
    private LocalDateTime lastUpdatedDate;
    private boolean jobSeekerVerifiedCompletion;
    private boolean employerVerifiedCompletion;
}
