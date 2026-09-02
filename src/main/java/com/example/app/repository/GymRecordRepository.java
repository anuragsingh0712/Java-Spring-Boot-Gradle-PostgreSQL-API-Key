package com.example.app.repository;

import com.example.app.entity.GymRecord;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRecordRepository extends JpaRepository<GymRecord, UUID> {
  List<GymRecord> findByRecordTypeOrderByCreatedAtDesc(String recordType);
}
