package com.skillnest.jobSeekerService.data.repository;

import com.skillnest.jobSeekerService.data.model.VerificationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VerificationDocumentRepository extends MongoRepository<VerificationDocument, String> {
}
