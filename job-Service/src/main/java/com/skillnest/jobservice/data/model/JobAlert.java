package com.skillnest.jobservice.data.model;

import com.skillnest.jobservice.data.enums.AlertFrequency;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("job_alert")
public class JobAlert {
    @Id
    private String id;

    private Long jobSeekerId;

    private String alertName;

    private List<String> searchWords;

    private String location;

    private BigDecimal minProposedPayment;

    private String jobType;

    private AlertFrequency notificationFrequency;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime lastNotifiedAt;

    private boolean active =true;

}
