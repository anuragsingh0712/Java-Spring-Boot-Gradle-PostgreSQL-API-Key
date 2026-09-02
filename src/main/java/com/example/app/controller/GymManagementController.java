package com.example.app.controller;

import com.example.app.dto.RecordRequest;
import com.example.app.dto.RecordResponse;
import com.example.app.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Gym Management")
public class GymManagementController {
  private final RecordService service;

  public GymManagementController(RecordService service) {
    this.service = service;
  }

  @Operation(summary = "Create a gym management resource")
  @PostMapping(
      "/{type:gyms|branches|members|trainers|workouts|classes|appointments|attendance|payments|notifications}")
  public ResponseEntity<RecordResponse> create(
      @PathVariable String type, @Valid @RequestBody RecordRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(type, request));
  }

  @Operation(summary = "List gym management resources")
  @GetMapping(
      "/{type:gyms|branches|members|trainers|workouts|classes|appointments|attendance|payments|notifications}")
  public List<RecordResponse> list(@PathVariable String type) {
    return service.list(type);
  }

  @Operation(summary = "Get a gym management resource")
  @GetMapping(
      "/{type:gyms|branches|members|trainers|workouts|classes|appointments|attendance|payments|notifications}/{id}")
  public RecordResponse get(@PathVariable String type, @PathVariable UUID id) {
    return service.get(type, id);
  }

  @Operation(summary = "Update a gym management resource")
  @PutMapping(
      "/{type:gyms|branches|members|trainers|workouts|classes|appointments|attendance|payments|notifications}/{id}")
  public RecordResponse update(
      @PathVariable String type, @PathVariable UUID id, @Valid @RequestBody RecordRequest request) {
    return service.update(type, id, request);
  }

  @Operation(summary = "Delete a gym management resource")
  @DeleteMapping(
      "/{type:gyms|branches|members|trainers|workouts|classes|appointments|attendance|payments|notifications}/{id}")
  public ResponseEntity<Void> delete(@PathVariable String type, @PathVariable UUID id) {
    service.delete(type, id);
    return ResponseEntity.noContent().build();
  }
}
