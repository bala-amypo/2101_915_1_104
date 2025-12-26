package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "issued_device_records")
public class IssuedDeviceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private Long deviceItemId;

    private LocalDateTime issuedAt;

    private Boolean returned;

    private LocalDateTime returnedAt;

    // ===== GETTERS =====

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Long getDeviceItemId() {
        return deviceItemId;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public Boolean getReturned() {
        return returned;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    // ===== SETTERS (REQUIRED BY TESTS & SERVICES) =====

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setDeviceItemId(Long deviceItemId) {
        this.deviceItemId = deviceItemId;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }
}
