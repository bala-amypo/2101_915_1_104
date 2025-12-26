package com.example.demo.repository;

import com.example.demo.model.IssuedDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IssuedDeviceRecordRepository
        extends JpaRepository<IssuedDeviceRecord, Long> {

    @Query("""
       SELECT COUNT(r) FROM IssuedDeviceRecord r
       WHERE r.employeeId = :employeeId AND r.returnedDate IS NULL
    """)
    long countActiveDevicesForEmployee(@Param("employeeId") Long employeeId);

    @Query("""
       SELECT r FROM IssuedDeviceRecord r
       WHERE r.employeeId = :employeeId
         AND r.deviceItemId = :deviceItemId
         AND r.returnedDate IS NULL
    """)
    Optional<IssuedDeviceRecord> findActiveByEmployeeAndDevice(
            @Param("employeeId") Long employeeId,
            @Param("deviceItemId") Long deviceItemId
    );

    List<IssuedDeviceRecord> findByEmployeeId(Long employeeId);
}
