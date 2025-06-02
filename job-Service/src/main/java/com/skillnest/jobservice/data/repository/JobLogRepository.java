package com.skillnest.jobservice.data.repository;

import com.skillnest.jobservice.data.model.JobLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobLogRepository extends MongoRepository<JobLog, String> {
}
