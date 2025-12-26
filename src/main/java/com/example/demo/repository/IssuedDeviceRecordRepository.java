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
        SELECT COUNT(i)
        FROM IssuedDeviceRecord i
        WHERE i.employeeId = :employeeId
          AND i.returnedDate IS NULL
    """)
    long countActiveDevicesForEmployee(@Param("employeeId") Long employeeId);

    @Query("""
        SELECT i
        FROM IssuedDeviceRecord i
        WHERE i.employeeId = :employeeId
          AND i.deviceItemId = :deviceItemId
          AND i.returnedDate IS NULL
    """)
    Optional<IssuedDeviceRecord> findActiveByEmployeeAndDevice(
            @Param("employeeId") Long employeeId,
            @Param("deviceItemId") Long deviceItemId
    );

    List<IssuedDeviceRecord> findByEmployeeId(Long employeeId);
}
