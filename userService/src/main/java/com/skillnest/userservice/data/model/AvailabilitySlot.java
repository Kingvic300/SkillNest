package com.skillnest.userservice.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class AvailabilitySlot {
    @Id
    private String id;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isAvailable;
}
