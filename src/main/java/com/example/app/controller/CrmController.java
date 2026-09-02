package com.example.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/crm")
@Tag(name = "CRM")
public class CrmController {

  @Operation(summary = "Check CRM system availability")
  @GetMapping
  public ResponseEntity<Map<String, String>> status() {
    return ResponseEntity.ok(Map.of("message", "CRM system is operational"));
  }
}
