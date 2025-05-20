package com.skillnest.userservice.data.repositories;

import com.skillnest.userservice.data.model.JobSeeker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobSeekerRepository extends MongoRepository<JobSeeker, String> {
    Optional<JobSeeker> findByUsername(String username);

    Optional<UserDetails> findJobSeekerByUsername(String username);
}

