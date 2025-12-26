package com.example.demo.service;

import java.util.List;
import com.example.demo.model.EligibilityCheck;

public interface EligibilityCheckService {

    boolean validateEligibility(Long employeeId, Long deviceItemId);

    List<EligibilityCheck> getChecksByEmployee(Long employeeId);
}
