package com.skillnest.jobservice.dtos.request;

import com.skillnest.jobservice.data.enums.AlertFrequency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class JobAlertRequest {
    @NotNull(message = "Jobseeker ID is mandatory for creating an alert")
    private Long jobSeekerId;

    @NotBlank(message = "Alert name is mandatory")
    private String alertName;

    private List<String> searchWords;

    private String location;

    private BigDecimal minPayment;

    private String jobType;

    @NotNull(message = "Notification frequency is mandatory")
    private AlertFrequency notificationFrequency;

    private boolean active = true;
}
