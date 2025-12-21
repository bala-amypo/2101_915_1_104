package com.example.demo.service;

import com.example.demo.model.PolicyRule;
import java.util.List;

public interface PolicyRuleService {
    PolicyRule create(PolicyRule policyRule);
    List<PolicyRule> getAll();
}
