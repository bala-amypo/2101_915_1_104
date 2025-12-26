package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import com.example.demo.service.IssuedDeviceRecordService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
            EligibilityCheckService eligibilityService
    ) {
        this.issuedRepo = issuedRepo;
        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
        this.eligibilityService = eligibilityService;
    }

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {

        employeeRepo.findById(record.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        deviceRepo.findById(record.getDeviceItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        if (issuedRepo.findActiveByEmployeeAndDevice(
                record.getEmployeeId(), record.getDeviceItemId()) != null) {
            throw new BadRequestException("Device already issued");
        }

        eligibilityService.validateEligibility(
                record.getEmployeeId(), record.getDeviceItemId());

        record.setIssuedDate(LocalDate.now());
        record.setStatus("ISSUED");
        record.setReturnedDate(null);

        return issuedRepo.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {

        IssuedDeviceRecord record = issuedRepo.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Issued record not found"));

        if (record.getReturnedDate() != null) {
            throw new BadRequestException("already returned");
        }

        record.setReturnedDate(LocalDate.now());
        record.setStatus("RETURNED");
        return issuedRepo.save(record);
    }

    // 🔴 THIS METHOD WAS MISSING – NOW FIXED
    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return issuedRepo.findByEmployeeId(employeeId);
    }
}
