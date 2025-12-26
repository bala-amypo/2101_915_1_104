package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;
    private final IssuedDeviceRecordRepository issuedRepo;
    private final PolicyRuleRepository ruleRepo;
    private final EligibilityCheckRecordRepository checkRepo;

    public EligibilityCheckServiceImpl(
            EmployeeProfileRepository employeeRepo,
            DeviceCatalogItemRepository deviceRepo,
            IssuedDeviceRecordRepository issuedRepo,
            PolicyRuleRepository ruleRepo,
            EligibilityCheckRecordRepository checkRepo
    ) {
        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
        this.issuedRepo = issuedRepo;
        this.ruleRepo = ruleRepo;
        this.checkRepo = checkRepo;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {

        EmployeeProfile employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        DeviceCatalogItem device = deviceRepo.findById(deviceItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployeeId(employeeId);
        record.setDeviceItemId(deviceItemId);

        // Employee inactive
        if (!Boolean.TRUE.equals(employee.getActive())) {
            record.setIsEligible(false);
            record.setReason("Employee inactive");
            return checkRepo.save(record);
        }

        // Device inactive
        if (!Boolean.TRUE.equals(device.getActive())) {
            record.setIsEligible(false);
            record.setReason("Device inactive");
            return checkRepo.save(record);
        }

        long activeCount = issuedRepo.countActiveDevicesForEmployee(employeeId);
        if (activeCount >= device.getMaxAllowedPerEmployee()) {
            record.setIsEligible(false);
            record.setReason("Device limit exceeded");
            return checkRepo.save(record);
        }

        // Policy rules
        List<PolicyRule> rules = ruleRepo.findByActiveTrue();
        for (PolicyRule rule : rules) {
            if ((rule.getAppliesToRole() == null ||
                 rule.getAppliesToRole().equals(employee.getJobRole())) &&
                (rule.getAppliesToDepartment() == null ||
                 rule.getAppliesToDepartment().equals(employee.getDepartment()))) {

                if (activeCount >= rule.getMaxDevicesAllowed()) {
                    record.setIsEligible(false);
                    record.setReason("Policy rule violation");
                    return checkRepo.save(record);
                }
            }
        }

        record.setIsEligible(true);
        record.setReason("Eligible");
        return checkRepo.save(record);
    }

    // 🔴 THIS METHOD WAS MISSING – NOW FIXED
    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return checkRepo.findByEmployeeId(employeeId);
    }
}
