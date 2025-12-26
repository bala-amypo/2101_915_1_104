package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;
    private final IssuedDeviceRecordRepository issuedRepo;
    private final PolicyRuleRepository ruleRepo;
    private final EligibilityCheckRecordRepository checkRepo;

    // ✅ Constructor EXACTLY as tests expect
    public EligibilityCheckServiceImpl(EmployeeProfileRepository employeeRepo,
                                       DeviceCatalogItemRepository deviceRepo,
                                       IssuedDeviceRecordRepository issuedRepo,
                                       PolicyRuleRepository ruleRepo,
                                       EligibilityCheckRecordRepository checkRepo) {
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
        record.setCheckedAt(LocalDateTime.now());

        // 1️⃣ Employee active check
        if (!Boolean.TRUE.equals(employee.getActive())) {
            record.setIsEligible(false);
            record.setReason("Employee inactive");
            return checkRepo.save(record);
        }

        // 2️⃣ Device active check
        if (!Boolean.TRUE.equals(device.getActive())) {
            record.setIsEligible(false);
            record.setReason("Device inactive");
            return checkRepo.save(record);
        }

        // 3️⃣ Existing active device limit (per device)
        long activeDeviceCount =
                issuedRepo.countActiveDevicesForEmployee(employeeId);

        if (activeDeviceCount >= device.getMaxAllowedPerEmployee()) {
            record.setIsEligible(false);
            record.setReason("Device limit exceeded");
            return checkRepo.save(record);
        }

        // 4️⃣ Policy rule enforcement
        List<PolicyRule> activeRules = ruleRepo.findByActiveTrue();
        for (PolicyRule rule : activeRules) {

            boolean roleMatch =
                    rule.getAppliesToRole() == null ||
                    rule.getAppliesToRole().equalsIgnoreCase(employee.getJobRole());

            boolean deptMatch =
                    rule.getAppliesToDepartment() == null ||
                    rule.getAppliesToDepartment().equalsIgnoreCase(employee.getDepartment());

            if (roleMatch && deptMatch) {
                if (activeDeviceCount >= rule.getMaxDevicesAllowed()) {
                    record.setIsEligible(false);
                    record.setReason("Policy rule limit exceeded");
                    return checkRepo.save(record);
                }
            }
        }

        // ✅ Eligible
        record.setIsEligible(true);
        record.setReason("Eligible");

        return checkRepo.save(record);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return checkRepo.findByEmployeeId(employeeId);
    }
}
