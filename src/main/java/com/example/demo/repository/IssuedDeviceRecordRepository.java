package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.IssuedDeviceRecord;

@Repository
public interface IssuedDeviceRecordRepository extends JpaRepository<IssuedDeviceRecord, Long> {

    @Query("""
        SELECT COUNT(i)
        FROM IssuedDeviceRecord i
        WHERE i.employeeId = :employeeId
          AND i.returnedDate IS NULL
    """)
    long countActiveDevicesForEmployee(Long employeeId);

    @Query("""
        SELECT i
        FROM IssuedDeviceRecord i
        WHERE i.employeeId = :employeeId
          AND i.deviceItemId = :deviceItemId
          AND i.returnedDate IS NULL
    """)
    IssuedDeviceRecord findActiveByEmployeeAndDevice(Long employeeId, Long deviceItemId);

    List<IssuedDeviceRecord> findByEmployeeId(Long employeeId);
}
