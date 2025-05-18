package com.skillnest.userservice.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
public class JobSeeker extends User{

    private List<String> skillIds;
    private double rating;
    private boolean verificationStatus;
    private String walletId;
    private List<AvailabilitySlot> availabilitySlotIds;
    private String bankAccountId;
}
