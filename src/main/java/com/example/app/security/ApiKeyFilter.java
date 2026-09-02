package com.example.app.security;

import com.example.app.entity.ApiKey;
import com.example.app.repository.ApiKeyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
  private final ApiKeyRepository repository;

  public ApiKeyFilter(ApiKeyRepository repository) {
    this.repository = repository;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    return !path.startsWith("/api/v1/") || path.startsWith("/api/v1/api-keys");
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String raw = request.getHeader("X-API-Key");
    if (raw == null || raw.isBlank()) {
      response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing API key");
      return;
    }
    try {
      String hash = toHash(raw);
      ApiKey key = repository.findByKeyHashAndActiveTrue(hash).orElse(null);
      if (key == null
          || (key.getExpiresAt() != null && key.getExpiresAt().isBefore(Instant.now()))) {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or expired API key");
        return;
      }
      key.setLastUsedAt(Instant.now());
      repository.save(key);
      SecurityContextHolder.getContext()
          .setAuthentication(
              new UsernamePasswordAuthenticationToken(key.getId().toString(), null, List.of()));
    } catch (Exception exception) {
      response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid API key");
      return;
    }
    chain.doFilter(request, response);
  }

  private String toHash(String value) throws Exception {
    byte[] hash =
        MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
    StringBuilder result = new StringBuilder();
    for (byte item : hash) {
      result.append(String.format("%02x", item));
    }
    return result.toString();
  }
}
