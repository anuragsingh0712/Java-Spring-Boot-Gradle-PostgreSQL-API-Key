package com.example.app.repository;

import com.example.app.entity.ApiKey;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiKeyRepository extends MongoRepository<ApiKey, UUID> {
  Optional<ApiKey> findByKeyHashAndActiveTrue(String keyHash);
}
