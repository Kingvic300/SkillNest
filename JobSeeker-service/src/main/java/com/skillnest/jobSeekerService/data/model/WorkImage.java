package com.skillnest.jobSeekerService.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("workImage")
public class WorkImage {
    @Id
    private String id;
    private String jobSeekerId;
    private String imageUrl;
    private String description;
    private LocalDateTime uploadedAt;
}
