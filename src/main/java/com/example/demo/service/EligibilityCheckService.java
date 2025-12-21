package com.example.eligibility.service;

import java.util.List;
import com.example.eligibility.model.EligibilityCheck;

public interface EligibilityCheckService {

    boolean validateEligibility(Long employeeId, Long deviceItemId);

    List<EligibilityCheck> getChecksByEmployee(Long employeeId);
}
