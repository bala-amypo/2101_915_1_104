package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.EligibilityCheckService;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    // ================= ISSUE DEVICE =================
    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {

        // 1️⃣ Employee validation
        EmployeeProfile employee = employeeRepo.findById(record.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (!Boolean.TRUE.equals(employee.getActive())) {
            throw new BadRequestException("Employee inactive");
        }

        // 2️⃣ Device validation
        DeviceCatalogItem device = deviceRepo.findById(record.getDeviceItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        if (!Boolean.TRUE.equals(device.getActive())) {
            throw new BadRequestException("Device inactive");
        }

        // 3️⃣ Duplicate active issuance check (FIXED Optional handling)
        Optional<IssuedDeviceRecord> existing =
                issuedRepo.findActiveByEmployeeAndDevice(
                        record.getEmployeeId(),
                        record.getDeviceItemId()
                );

        if (existing.isPresent()) {
            throw new BadRequestException("Device already issued to employee");
        }

        // 4️⃣ Eligibility validation (creates audit log)
        eligibilityService.validateEligibility(
                record.getEmployeeId(),
                record.getDeviceItemId()
        );

        // 5️⃣ Issue device
        record.setIssuedDate(LocalDate.now());
        record.setReturnedDate(null);
        record.setStatus("ISSUED");

        return issuedRepo.save(record);
    }

    // ================= RETURN DEVICE =================
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

    // ================= GET BY EMPLOYEE =================
    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return issuedRepo.findByEmployeeId(employeeId);
    }
}
