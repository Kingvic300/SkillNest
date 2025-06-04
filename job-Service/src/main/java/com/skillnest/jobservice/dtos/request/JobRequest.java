package com.skillnest.jobservice.dtos.request;

import com.skillnest.jobservice.data.enums.JobStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobRequest {
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> master
=======
>>>>>>> ac3d6e0bc6e11e6e70e49dfe0dbb46778dfa09ba
    private String title;
    private String description;
    private String employerId;
    private String location;
    private List<String> requiredSkillIds;
    private BigDecimal proposedPayment;
    private String jobType;
    private LocalDateTime deadline;
    private String contactInfo;
    private LocalDateTime postedDate;
}
