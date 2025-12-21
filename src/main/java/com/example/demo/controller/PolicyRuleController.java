package com.example.demo.controller;

import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policy-rules")
public class PolicyRuleController {

    @Autowired
    private PolicyRuleService service;

    @PostMapping
    public ResponseEntity<PolicyRule> create(@RequestBody PolicyRule policyRule) {
        PolicyRule created = service.create(policyRule);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<PolicyRule>> getAll() {
        List<PolicyRule> rules = service.getAll();
        return ResponseEntity.ok(rules);
    }
}
