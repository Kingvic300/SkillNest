package com.skillnest.jobSeekerService.data.model;

import com.skillnest.jobSeekerService.data.enums.VerificationStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("verification_document")
public class VerificationDocument {
    @Id
    private String id;
    private String jobSeekerId;
    private String type;
    private String documentUrl;
    private VerificationStatus status;
}
