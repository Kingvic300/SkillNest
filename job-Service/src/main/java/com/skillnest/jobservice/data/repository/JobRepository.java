package com.skillnest.jobservice.data.repository;

import com.skillnest.jobservice.data.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
}
