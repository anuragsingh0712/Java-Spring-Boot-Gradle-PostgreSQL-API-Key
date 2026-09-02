package com.example.app.repository;

import com.example.app.entity.GymRecord;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GymRecordRepository extends MongoRepository<GymRecord, UUID> {
  List<GymRecord> findByRecordTypeOrderByCreatedAtDesc(String recordType);
}
