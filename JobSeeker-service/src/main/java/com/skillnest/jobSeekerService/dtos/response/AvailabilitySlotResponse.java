package com.skillnest.jobSeekerService.dtos.response;

import com.skillnest.jobSeekerService.data.model.AvailabilitySlot;
import com.skillnest.jobSeekerService.data.model.JobSeeker;
import lombok.Data;

@Data
public class AvailabilitySlotResponse {
    private String message;
    private AvailabilitySlot availabilitySlot;
}
