package com.example.demo.repository;

import com.example.demo.model.IssuedDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IssuedDeviceRecordRepository extends JpaRepository<IssuedDeviceRecord, Long> {

    @Query("""
        SELECT COUNT(r) FROM IssuedDeviceRecord r
        WHERE r.employeeId = :employeeId AND r.returnedDate IS NULL
    """)
    long countActiveDevicesForEmployee(Long employeeId);

    @Query("""
        SELECT r FROM IssuedDeviceRecord r
        WHERE r.employeeId = :employeeId
          AND r.deviceItemId = :deviceItemId
          AND r.returnedDate IS NULL
    """)
    Optional<IssuedDeviceRecord> findActiveByEmployeeAndDevice(Long employeeId, Long deviceItemId);

    List<IssuedDeviceRecord> findByEmployeeId(Long employeeId);
}
