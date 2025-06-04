package com.skillnest.jobSeekerService.data.repository;

import com.skillnest.jobSeekerService.data.model.JobSeeker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JobSeekerRepository extends MongoRepository<JobSeeker,String> {
    Optional<JobSeeker> findByUserId(String userid);
}
