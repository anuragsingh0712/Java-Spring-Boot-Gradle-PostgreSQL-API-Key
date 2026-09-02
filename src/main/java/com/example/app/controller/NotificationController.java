package com.example.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
@Tag(name = "Notification")
public class NotificationController {

  @Operation(summary = "Check notification service availability")
  @GetMapping
  public Map<String, String> status() {
    return Map.of("message", "Notification endpoint is operational");
  }
}
