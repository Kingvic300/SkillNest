package com.skillnest.jobservice.data.repository;

import com.skillnest.jobservice.data.model.WorkImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkImageRepository extends MongoRepository<WorkImage, String> {
}
