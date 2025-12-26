package com.example.demo.service.impl;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository repo;

    public IssuedDeviceRecordServiceImpl(IssuedDeviceRecordRepository repo) {
        this.repo = repo;
    }

    @Override
    public IssuedDeviceRecord issueDevice(Long employeeId, Long deviceItemId) {
        IssuedDeviceRecord record = new IssuedDeviceRecord();
        record.setEmployeeId(employeeId);
        record.setDeviceItemId(deviceItemId);
        record.setIssuedDate(LocalDate.now());
        record.setReturned(false);
        return repo.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long issuedRecordId) {
        IssuedDeviceRecord record =
                repo.findById(issuedRecordId).orElseThrow();
        record.setReturned(true);
        record.setReturnDate(LocalDate.now());
        return repo.save(record);
    }

    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return repo.findByEmployeeId(employeeId);
    }

    @Override
    public List<IssuedDeviceRecord> getAll() {
        return repo.findAll();
    }
}
