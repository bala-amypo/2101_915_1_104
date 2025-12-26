package com.example.demo.repository;

import com.example.demo.model.IssuedDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssuedDeviceRecordRepository extends JpaRepository<IssuedDeviceRecord, Long> {

    List<IssuedDeviceRecord> findByEmployeeId(Long employeeId);

    List<IssuedDeviceRecord> findByEmployeeIdAndReturnedFalse(Long employeeId);

    // ✅ REQUIRED BY TESTS
    List<IssuedDeviceRecord> findActiveByEmployeeAndDevice(Long employeeId, Long deviceItemId);

    long countActiveDevicesForEmployee(Long employeeId);
}
