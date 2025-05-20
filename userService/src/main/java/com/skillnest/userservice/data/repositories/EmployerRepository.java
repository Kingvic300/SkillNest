package com.skillnest.userservice.data.repositories;

import com.skillnest.userservice.data.model.Employer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends MongoRepository<Employer, String> {
    Optional<Employer> findByUsername(String username);

    Optional<UserDetails> findEmployerByUsername(String username);
}
