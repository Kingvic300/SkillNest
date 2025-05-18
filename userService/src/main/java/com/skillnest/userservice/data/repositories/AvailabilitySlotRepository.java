package com.skillnest.userservice.data.repositories;

import com.skillnest.userservice.data.model.AvailabilitySlot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilitySlotRepository extends MongoRepository<AvailabilitySlot, String> {
}
