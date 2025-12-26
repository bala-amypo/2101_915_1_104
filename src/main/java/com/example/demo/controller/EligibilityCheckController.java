package com.example.demo.controller;

import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityCheckController {

    private final EligibilityCheckService eligibilityCheckService;

    public EligibilityCheckController(EligibilityCheckService eligibilityCheckService) {
        this.eligibilityCheckService = eligibilityCheckService;
    }

    @PostMapping("/validate/{employeeId}/{deviceItemId}")
    public ResponseEntity<EligibilityCheckRecord> validate(@PathVariable Long employeeId,
                                                           @PathVariable Long deviceItemId) {
        return ResponseEntity.ok(
                eligibilityCheckService.validateEligibility(employeeId, deviceItemId)
        );
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EligibilityCheckRecord>> getHistory(@PathVariable Long employeeId) {
        return ResponseEntity.ok(
                eligibilityCheckService.getChecksByEmployee(employeeId)
        );
    }
}
