package com.skillnest.jobservice.dtos.response;

import com.skillnest.jobservice.data.enums.AlertFrequency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobAlertResponse {
    private Long id;
    private Long jobSeekerId;
    private String alertName;
    private List<String> keywords;
    private String location;
    private BigDecimal minProposedPayment;
    private String jobType;
    private AlertFrequency notificationFrequency;
    private LocalDateTime createdAt;
    private LocalDateTime lastNotifiedAt;
    private boolean active;
}
