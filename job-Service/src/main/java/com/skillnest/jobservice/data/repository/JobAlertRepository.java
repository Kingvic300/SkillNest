package com.skillnest.jobservice.data.repository;

import com.skillnest.jobservice.data.model.JobAlert;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobAlertRepository extends MongoRepository<JobAlert, String> {
}
