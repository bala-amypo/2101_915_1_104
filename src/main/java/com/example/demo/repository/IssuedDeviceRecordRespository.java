package com.example.demo.repository;

import com.example.demo.model.IssuedDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuedDeviceRecordRepository extends JpaRepository<IssuedDeviceRecord, Long> {
    
    // Required for limit enforcement [cite: 8, 196]
    @Query("SELECT COUNT(r) FROM IssuedDeviceRecord r WHERE r.employeeId = :empId AND r.returnedDate IS NULL")
    long countActiveDevicesForEmployee(@Param("empId") Long employeeId);

    // Required to prevent duplicate issuances [cite: 8, 196]
    @Query("SELECT r FROM IssuedDeviceRecord r WHERE r.employeeId = :empId AND r.deviceItemId = :devId AND r.returnedDate IS NULL")
    IssuedDeviceRecord findActiveByEmployeeAndDevice(@Param("empId") Long employeeId, @Param("devId") Long deviceItemId);
}