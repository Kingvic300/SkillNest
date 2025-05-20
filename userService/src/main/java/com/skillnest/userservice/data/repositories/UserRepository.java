package com.skillnest.userservice.data.repositories;

import com.skillnest.userservice.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<UserDetails> findUserByUsername(String username);

    Optional<User> findByEmail(String email);
}

