package com.example.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record RecordRequest(
    @NotBlank String title,
    UUID memberId,
    @NotBlank String status,
    Instant scheduledAt,
    @PositiveOrZero BigDecimal amount,
    String details) {}
