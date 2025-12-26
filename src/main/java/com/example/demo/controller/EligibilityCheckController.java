package com.example.demo.controller;

import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eligibility")
public class EligibilityCheckController {

    private final EligibilityCheckService service;

    public EligibilityCheckController(EligibilityCheckService service) {
        this.service = service;
    }

    @PostMapping
    public EligibilityCheckRecord check(@RequestParam Long employeeId,
                                        @RequestParam Long deviceItemId) {
        return service.validateEligibility(employeeId, deviceItemId);
    }

    @GetMapping("/{employeeId}")
    public List<EligibilityCheckRecord> byEmployee(@PathVariable Long employeeId) {
        return service.getChecksByEmployee(employeeId);
    }
}
