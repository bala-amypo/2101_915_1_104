package com.example.demo.service.impl;

import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PolicyRuleServiceImpl implements PolicyRuleService {

    // For simplicity, using in-memory list; replace with repository in real project
    private final List<PolicyRule> rules = new ArrayList<>();

    @Override
    public PolicyRule create(PolicyRule policyRule) {
        rules.add(policyRule);
        return policyRule;
    }

    @Override
    public List<PolicyRule> getAll() {
        return new ArrayList<>(rules);
    }
}
