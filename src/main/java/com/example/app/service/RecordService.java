package com.example.app.service;

import com.example.app.dto.RecordRequest;
import com.example.app.dto.RecordResponse;
import com.example.app.entity.GymRecord;
import com.example.app.repository.GymRecordRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RecordService {
  private final GymRecordRepository repository;

  public RecordService(GymRecordRepository repository) {
    this.repository = repository;
  }

  public RecordResponse create(String type, RecordRequest request) {
    GymRecord record = new GymRecord();
    record.setId(UUID.randomUUID());
    record.setRecordType(type);
    copy(record, request);
    return response(repository.save(record));
  }

  public List<RecordResponse> list(String type) {
    return repository.findByRecordTypeOrderByCreatedAtDesc(type).stream()
        .map(this::response)
        .toList();
  }

  public RecordResponse get(String type, UUID id) {
    return response(existing(type, id));
  }

  public RecordResponse update(String type, UUID id, RecordRequest request) {
    GymRecord record = existing(type, id);
    copy(record, request);
    return response(repository.save(record));
  }

  public void delete(String type, UUID id) {
    repository.delete(existing(type, id));
  }

  private GymRecord existing(String type, UUID id) {
    GymRecord r =
        repository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
    if (!r.getRecordType().equals(type))
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
    return r;
  }

  private void copy(GymRecord r, RecordRequest q) {
    r.setTitle(q.title());
    r.setMemberId(q.memberId());
    r.setStatus(q.status());
    r.setScheduledAt(q.scheduledAt());
    r.setAmount(q.amount());
    r.setDetails(q.details());
  }

  private RecordResponse response(GymRecord r) {
    return new RecordResponse(
        r.getId(),
        r.getRecordType(),
        r.getTitle(),
        r.getMemberId(),
        r.getStatus(),
        r.getScheduledAt(),
        r.getAmount(),
        r.getDetails());
  }
}
