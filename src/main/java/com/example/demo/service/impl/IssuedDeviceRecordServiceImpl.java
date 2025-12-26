package com.example.demo.service.impl;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;

import java.time.LocalDateTime;

public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository issuedRepo;

    // EXACT constructor used in tests
    public IssuedDeviceRecordServiceImpl(IssuedDeviceRecordRepository issuedRepo) {
        this.issuedRepo = issuedRepo;
    }

    @Override
    public IssuedDeviceRecord issueDevice(Long employeeId, Long deviceItemId) {

        IssuedDeviceRecord record = new IssuedDeviceRecord();
        record.setEmployeeId(employeeId);
        record.setDeviceItemId(deviceItemId);
        record.setIssuedAt(LocalDateTime.now());
        record.setReturned(Boolean.FALSE);
        record.setStatus("ISSUED");

        return issuedRepo.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {

        IssuedDeviceRecord record = issuedRepo.findById(recordId).orElse(null);
        if (record != null) {
            record.setReturned(Boolean.TRUE);
            record.setReturnedDate(LocalDateTime.now());
            record.setStatus("RETURNED");
            issuedRepo.save(record);
        }
        return record;
    }
}
