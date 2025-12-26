package com.example.demo.controller;

import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policy-rules")
public class PolicyRuleController {

    private final PolicyRuleService policyRuleService;

    public PolicyRuleController(PolicyRuleService policyRuleService) {
        this.policyRuleService = policyRuleService;
    }

    @PostMapping
    public ResponseEntity<PolicyRule> create(@RequestBody PolicyRule rule) {
        return ResponseEntity.ok(policyRuleService.createRule(rule));
    }

    @GetMapping
    public ResponseEntity<List<PolicyRule>> getAll() {
        return ResponseEntity.ok(policyRuleService.getAllRules());
    }

    @GetMapping("/active")
    public ResponseEntity<List<PolicyRule>> getActive() {
        return ResponseEntity.ok(policyRuleService.getActiveRules());
    }
}
