package com.example.demo.service.impl;

import com.example.demo.model.EligibilityCheck;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final List<EligibilityCheck> checks = new ArrayList<>();

    @Override
    public boolean validateEligibility(Long employeeId, Long deviceItemId) {
        EligibilityCheck check = new EligibilityCheck();
        check.setEmployeeId(employeeId);
        check.setDeviceItemId(deviceItemId);
        check.setEligible(true); // dummy logic
        checks.add(check);
        return true;
    }

    @Override
    public List<EligibilityCheck> getChecksByEmployee(Long employeeId) {
        return checks.stream()
                .filter(c -> c.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }
}
