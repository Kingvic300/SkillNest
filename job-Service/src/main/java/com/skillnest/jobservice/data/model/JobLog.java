package com.skillnest.jobservice.data.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("job_log")
public class JobLog {
    @Id
    private String id;

    private Job job;

    private String updateMessage;

    private String updaterId;

    @CreatedDate
    private LocalDateTime logTime;

}
