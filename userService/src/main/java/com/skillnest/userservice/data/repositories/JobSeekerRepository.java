package com.skillnest.userservice.data.repositories;

import com.skillnest.userservice.data.model.JobSeeker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSeekerRepository extends MongoRepository<JobSeeker, String> {
}
