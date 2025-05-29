package com.skillnest.jobSeekerService.data.repository;

import com.skillnest.jobSeekerService.data.model.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
}
