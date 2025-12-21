package com.example.policy.controller;

import com.example.policy.model.PolicyRule;
import com.example.policy.service.PolicyRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policy-rules")
public class PolicyRuleController {

    private final PolicyRuleService service;

    public PolicyRuleController(PolicyRuleService service) {
        this.service = service;
    }

    @PostMapping
    public PolicyRule createRule(@RequestBody PolicyRule rule) {
        return service.createRule(rule);
    }

    @GetMapping("/active")
    public List<PolicyRule> getActiveRules() {
        return service.getActiveRules();
    }

    @GetMapping
    public List<PolicyRule> getAllRules() {
        return service.getAllRules();
    }
}
