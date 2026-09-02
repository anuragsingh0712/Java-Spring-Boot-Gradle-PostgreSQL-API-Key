package com.example.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/biometric")
@Tag(name = "Biometric")
public class BiometricController {

  @Operation(summary = "Check biometric service availability")
  @GetMapping
  public Map<String, String> status() {
    return Map.of("message", "Biometric endpoint is operational");
  }
}
