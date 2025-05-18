package com.skillnest.userservice.data.repositories;

import com.skillnest.userservice.data.model.Employer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends MongoRepository<Employer, String> {
}
