package com.example.app.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record RecordResponse(
    UUID id,
    String type,
    String title,
    UUID memberId,
    String status,
    Instant scheduledAt,
    BigDecimal amount,
    String details) {}
