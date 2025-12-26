package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import com.example.demo.service.IssuedDeviceRecordService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository issuedRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;
    private final EligibilityCheckService eligibilityService;

    public IssuedDeviceRecordServiceImpl(
            IssuedDeviceRecordRepository issuedRepo,
            EmployeeProfileRepository employeeRepo,
            DeviceCatalogItemRepository deviceRepo,
            EligibilityCheckService eligibilityService) {

        this.issuedRepo = issuedRepo;
        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
        this.eligibilityService = eligibilityService;
    }

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {

        EligibilityCheckRecord check =
                eligibilityService.validateEligibility(
                        record.getEmployeeId(),
                        record.getDeviceItemId()
                );

        if (!check.getIsEligible()) {
            throw new RuntimeException(check.getReason());
        }

        IssuedDeviceRecord existing =
                issuedRepo.findActiveByEmployeeAndDevice(
                        record.getEmployeeId(),
                        record.getDeviceItemId()
                );

        if (existing != null) {
            throw new RuntimeException("Device already issued");
        }

        record.setIssuedDate(LocalDate.now());
        record.setReturnedDate(null);
        record.setStatus("ISSUED");

        return issuedRepo.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {

        IssuedDeviceRecord record = issuedRepo.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (record.getReturnedDate() != null) {
            throw new RuntimeException("already returned");
        }

        record.setReturnedDate(LocalDate.now());
        record.setStatus("RETURNED");

        return issuedRepo.save(record);
    }
}
