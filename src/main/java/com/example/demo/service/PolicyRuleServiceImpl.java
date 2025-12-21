package com.example.demo.service.impl;

import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyRuleServiceImpl implements PolicyRuleService {

    private final List<PolicyRule> rules = new ArrayList<>();

    @Override
    public PolicyRule createRule(PolicyRule rule) {
        rules.add(rule);
        return rule;
    }

    @Override
    public List<PolicyRule> getActiveRules() {
        return rules.stream()
                .filter(PolicyRule::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public List<PolicyRule> getAllRules() {
        return rules;
    }
}
