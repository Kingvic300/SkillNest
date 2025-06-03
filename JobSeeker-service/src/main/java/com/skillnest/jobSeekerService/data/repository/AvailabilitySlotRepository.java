package com.skillnest.jobSeekerService.data.repository;

import com.skillnest.jobSeekerService.data.model.AvailabilitySlot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilitySlotRepository extends MongoRepository<AvailabilitySlot, String> {
}
