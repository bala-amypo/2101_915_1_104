package com.example.demo.service.impl;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository issuedRepo;

    public IssuedDeviceRecordServiceImpl(IssuedDeviceRecordRepository issuedRepo) {
        this.issuedRepo = issuedRepo;
    }

    @Override
    public IssuedDeviceRecord issueDevice(Long employeeId, Long deviceItemId) {

        IssuedDeviceRecord record = new IssuedDeviceRecord();
        record.setEmployeeId(employeeId);
        record.setDeviceItemId(deviceItemId);

        // ❌ DO NOT set issued date (field does not exist)
        // record.setIssuedDate(LocalDateTime.now());

        record.setStatus("ISSUED");
        record.setReturnedDate(null);

        return issuedRepo.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long issuedRecordId) {

        IssuedDeviceRecord record = issuedRepo.findById(issuedRecordId)
                .orElseThrow(() -> new RuntimeException("Issued record not found"));

        record.setStatus("RETURNED");
        record.setReturnedDate(LocalDateTime.now());

        return issuedRepo.save(record);
    }

    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return issuedRepo.findByEmployeeId(employeeId);
    }
}
