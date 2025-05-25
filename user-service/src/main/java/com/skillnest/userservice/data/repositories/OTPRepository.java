package com.skillnest.userservice.data.repositories;

import com.skillnest.userservice.data.model.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends MongoRepository<OTP, String> {
    Optional<OTP> findByEmailAndOtp(String email, String otp);

    boolean existsByOtp(String otp);

    Optional<OTP> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
