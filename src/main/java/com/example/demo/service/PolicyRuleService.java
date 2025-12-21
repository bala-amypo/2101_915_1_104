package com.example.policy.service;

import java.util.List;
import com.example.policy.model.PolicyRule;

public interface PolicyRuleService {

    PolicyRule createRule(PolicyRule rule);

    List<PolicyRule> getAllRules();

    List<PolicyRule> getActiveRules();
}
