package com.example.demo.service.impl;

import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.repository.EligibilityCheckRecordRepository;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EligibilityCheckRecordRepository repo;

    public EligibilityCheckServiceImpl(EligibilityCheckRecordRepository repo) {
        this.repo = repo;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {
        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployeeId(employeeId);
        record.setDeviceItemId(deviceItemId);
        record.setIsEligible(true);
        record.setReason("ELIGIBLE");
        return repo.save(record);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return repo.findByEmployeeId(employeeId);
    }
}
