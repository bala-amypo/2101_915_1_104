package com.example.demo.service.impl;

import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.model.PolicyRule;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.EligibilityCheckRecordRepository;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.repository.PolicyRuleRepository;
import com.example.demo.service.EligibilityCheckService;

import java.util.List;

public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;
    private final IssuedDeviceRecordRepository issuedRepo;
    private final PolicyRuleRepository ruleRepo;
    private final EligibilityCheckRecordRepository recordRepo;

    // 🔴 EXACT constructor expected by TestNG tests
    public EligibilityCheckServiceImpl(
            EmployeeProfileRepository employeeRepo,
            DeviceCatalogItemRepository deviceRepo,
            IssuedDeviceRecordRepository issuedRepo,
            PolicyRuleRepository ruleRepo,
            EligibilityCheckRecordRepository recordRepo
    ) {
        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
        this.issuedRepo = issuedRepo;
        this.ruleRepo = ruleRepo;
        this.recordRepo = recordRepo;
    }

    /**
     * Perform eligibility check and ALWAYS persist EligibilityCheckRecord
     */
    @Override
    public EligibilityCheckRecord checkEligibility(Long employeeId, String deviceCode) {

        EmployeeProfile employee = employeeRepo.findById(employeeId)
                .orElse(null);

        DeviceCatalogItem device = deviceRepo.findByDeviceCode(deviceCode);

        boolean eligible = true;
        String reason = "Eligible";

        // Employee validation
        if (employee == null || !employee.isActive()) {
            eligible = false;
            reason = "Employee not active or not found";
        }

        // Device validation
        if (eligible && (device == null || !device.isActive())) {
            eligible = false;
            reason = "Device not active or not found";
        }

        // Active issued device count check
        if (eligible) {
            long activeCount = issuedRepo.countActiveDevicesForEmployee(employeeId);
            if (activeCount >= device.getMaxAllowedPerEmployee()) {
                eligible = false;
                reason = "maxAllowedPerEmployee exceeded";
            }
        }

        // Policy rules check (ONLY active rules)
        if (eligible) {
            List<PolicyRule> rules = ruleRepo.findByActiveTrue();
            for (PolicyRule rule : rules) {
                if (!rule.isEligible(employee, device)) {
                    eligible = false;
                    reason = rule.getRuleCode();
                    break;
                }
            }
        }

        // 🔴 ALWAYS create eligibility record (even if not eligible)
        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployee(employee);
        record.setDevice(device);
        record.setEligible(eligible);
        record.setReason(reason);

        recordRepo.save(record);

        return record;
    }

    /**
     * REQUIRED by interface – tests call this directly
     */
    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return recordRepo.findByEmployeeId(employeeId);
    }
}
