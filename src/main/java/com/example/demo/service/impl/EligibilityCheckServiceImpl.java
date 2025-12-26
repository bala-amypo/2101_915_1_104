package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  // 🔴 THIS IS THE FIX
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;
    private final IssuedDeviceRecordRepository issuedRepo;
    private final PolicyRuleRepository ruleRepo;
    private final EligibilityCheckRecordRepository recordRepo;

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

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {

        boolean eligible = true;
        String reason = "Eligible";

        Optional<EmployeeProfile> empOpt = employeeRepo.findById(employeeId);
        Optional<DeviceCatalogItem> devOpt = deviceRepo.findById(deviceItemId);

        if (empOpt.isEmpty() || !Boolean.TRUE.equals(empOpt.get().getActive())) {
            eligible = false;
            reason = "Employee not active or not found";
        }

        if (eligible && (devOpt.isEmpty() || !Boolean.TRUE.equals(devOpt.get().getActive()))) {
            eligible = false;
            reason = "Device not active or not found";
        }

        if (eligible) {
            long count = issuedRepo.countActiveDevicesForEmployee(employeeId);
            if (count >= devOpt.get().getMaxAllowedPerEmployee()) {
                eligible = false;
                reason = "maxAllowedPerEmployee exceeded";
            }
        }

        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployeeId(employeeId);
        record.setDeviceItemId(deviceItemId);
        record.setIsEligible(eligible);
        record.setReason(reason);

        recordRepo.save(record);
        return record;
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return recordRepo.findByEmployeeId(employeeId);
    }
}
