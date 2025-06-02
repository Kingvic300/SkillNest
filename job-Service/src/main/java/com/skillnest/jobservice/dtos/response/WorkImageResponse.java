package com.skillnest.jobservice.dtos.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkImageResponse {
    private String id;
    private String jobSeekerId;
    private String imageUrl;
    private String caption;
    private LocalDateTime uploadDate;
}
