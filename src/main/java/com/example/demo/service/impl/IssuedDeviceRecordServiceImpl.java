package com.example.demo.service.impl;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository issuedRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;

    public IssuedDeviceRecordServiceImpl(
            IssuedDeviceRecordRepository issuedRepo,
            EmployeeProfileRepository employeeRepo,
            DeviceCatalogItemRepository deviceRepo
    ) {
        this.issuedRepo = issuedRepo;
        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
    }

    @Override
    public IssuedDeviceRecord issueDevice(Long employeeId, Long deviceId) {
        IssuedDeviceRecord r = new IssuedDeviceRecord();
        r.setEmployeeId(employeeId);
        r.setDeviceItemId(deviceId);
        r.setStatus("ISSUED");
        r.setIssuedDate(LocalDateTime.now());
        return issuedRepo.save(r);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {
        IssuedDeviceRecord r = issuedRepo.findById(recordId)
                .orElseThrow(() -> new BadRequestException("Record not found"));

        if ("RETURNED".equals(r.getStatus())) {
            throw new BadRequestException("Device already returned");
        }

        r.setStatus("RETURNED");
        r.setReturnedDate(LocalDateTime.now());
        return issuedRepo.save(r);
    }

    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return issuedRepo.findByEmployeeId(employeeId);
    }
}
