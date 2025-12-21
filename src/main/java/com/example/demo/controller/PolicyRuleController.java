package com.example.demo.controller;

import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class PolicyRuleController {

    private final PolicyRuleService service;

    public PolicyRuleController(PolicyRuleService service) {
        this.service = service;
    }

    @PostMapping
    public PolicyRule create(@RequestBody PolicyRule rule) {
        return service.create(rule);
    }

    @GetMapping
    public List<PolicyRule> getAll() {
        return service.getAll();
    }
}
