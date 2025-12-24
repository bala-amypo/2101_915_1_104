package com.example.demo.repository;

import com.example.demo.model.IssuedDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuedDeviceRecordRepository extends JpaRepository<IssuedDeviceRecord, Long> {
    
    @Query("SELECT COUNT(r) FROM IssuedDeviceRecord r WHERE r.employeeId = :empId AND r.returnedDate IS NULL")
    long countActiveDevicesForEmployee(@Param("empId") Long employeeId);
}