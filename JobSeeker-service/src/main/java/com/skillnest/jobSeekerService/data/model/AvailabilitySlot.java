package com.skillnest.jobSeekerService.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Document("availability_slot")
public class AvailabilitySlot {
    @Id
    private String id;
    private String jobSeekerId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
