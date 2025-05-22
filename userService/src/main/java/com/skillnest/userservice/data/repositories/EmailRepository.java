package com.skillnest.userservice.data.repositories;

import com.skillnest.userservice.data.model.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends MongoRepository<Email, String> {
    Optional<Email> findByEmail(String email);

    void deleteByEmail(String email);
}
