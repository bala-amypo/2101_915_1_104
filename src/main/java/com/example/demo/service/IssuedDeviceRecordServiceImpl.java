package com.example.demo.service.impl;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final List<IssuedDeviceRecord> records = new ArrayList<>();

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {
        records.add(record);
        return record;
    }

    @Override
    public void returnDevice(Long recordId) {
        records.stream()
                .filter(r -> r.getId().equals(recordId))
                .findFirst()
                .ifPresent(r -> r.setReturned(true));
    }

    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return records.stream()
                .filter(r -> r.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }
}
