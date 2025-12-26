package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
            EligibilityCheckRecordRepository checkRepo) {

        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
        this.issuedRepo = issuedRepo;
        this.ruleRepo = ruleRepo;
        this.checkRepo = checkRepo;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {

        EmployeeProfile emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        DeviceCatalogItem device = deviceRepo.findById(deviceItemId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployeeId(employeeId);
        record.setDeviceItemId(deviceItemId);

        if (!emp.getActive()) {
            record.setIsEligible(false);
            record.setReason("Employee inactive");
            return checkRepo.save(record);
        }

        if (!device.getActive()) {
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

        record.setIsEligible(true);
        record.setReason("Eligible");
        return checkRepo.save(record);
    }
}
