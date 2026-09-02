package com.example.app.controller;

import com.example.app.entity.ApiKey;
import com.example.app.repository.ApiKeyRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/api-keys")
@Tag(name = "API Key Administration")
public class ApiKeyController {
  private final ApiKeyRepository repository;
  private final String adminKey;

  public ApiKeyController(ApiKeyRepository repository, @Value("${admin.api-key}") String adminKey) {
    this.repository = repository;
    this.adminKey = adminKey;
  }

  @Operation(summary = "Create an API key")
  @PostMapping
  public Map<String, String> create(
      @RequestHeader(value = "X-Admin-Key", required = false) String supplied,
      @RequestBody Map<String, String> body)
      throws Exception {
    verify(supplied);
    byte[] bytes = new byte[32];
    new SecureRandom().nextBytes(bytes);
    String raw = HexFormat.of().formatHex(bytes);
    ApiKey key = new ApiKey();
    key.setId(UUID.randomUUID());
    key.setName(body.getOrDefault("name", "API client"));
    key.setKeyHash(hash(raw));
    key.setCreatedAt(Instant.now());
    repository.save(key);
    return Map.of("apiKey", raw, "name", key.getName());
  }

  @Operation(summary = "List API key metadata")
  @PostMapping("/list")
  public List<Map<String, Object>> list(
      @RequestHeader(value = "X-Admin-Key", required = false) String supplied) {
    verify(supplied);
    return repository.findAll().stream()
        .map(
            k ->
                Map.<String, Object>of(
                    "id", k.getId(), "name", k.getName(), "active", k.isActive()))
        .toList();
  }

  @Operation(summary = "Revoke an API key")
  @DeleteMapping("/{id}")
  public void revoke(
      @RequestHeader(value = "X-Admin-Key", required = false) String supplied,
      @PathVariable UUID id) {
    verify(supplied);
    ApiKey key =
        repository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    key.setActive(false);
    repository.save(key);
  }

  private void verify(String supplied) {
    if (supplied == null
        || !MessageDigest.isEqual(
            supplied.getBytes(StandardCharsets.UTF_8), adminKey.getBytes(StandardCharsets.UTF_8)))
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
  }

  private String hash(String raw) throws Exception {
    return HexFormat.of()
        .formatHex(
            MessageDigest.getInstance("SHA-256").digest(raw.getBytes(StandardCharsets.UTF_8)));
  }
}
