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

    // ✅ REQUIRED BY TESTS
    private String status;

    private LocalDateTime issuedAt;

    private Boolean returned;

    // ✅ Tests use returnedDate (NOT returnedAt)
    private LocalDateTime returnedDate;

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

    public String getStatus() {
        return status;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public Boolean getReturned() {
        return returned;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    // ===== SETTERS (ALL REQUIRED BY TESTS) =====

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setDeviceItemId(Long deviceItemId) {
        this.deviceItemId = deviceItemId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public void setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
    }
}
