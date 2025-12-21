package com.example.demo.controller;

import com.example.demo.model.EligibilityCheck;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityCheckController {

    private final EligibilityCheckService service;

    public EligibilityCheckController(EligibilityCheckService service) {
        this.service = service;
    }

    @PostMapping("/validate/{employeeId}/{deviceItemId}")
    public boolean validate(
            @PathVariable Long employeeId,
            @PathVariable Long deviceItemId) {
        return service.validateEligibility(employeeId, deviceItemId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<EligibilityCheck> getChecks(@PathVariable Long employeeId) {
        return service.getChecksByEmployee(employeeId);
    }
}
