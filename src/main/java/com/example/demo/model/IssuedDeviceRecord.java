package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // 
@Table(name = "issued_device_records")
public class IssuedDeviceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private Long deviceItemId;

    private LocalDateTime issuedDate;
    private LocalDateTime returnedDate;
    private String status; // e.g., "ISSUED", "RETURNED"

    @PrePersist
    protected void onCreate() {
        this.issuedDate = LocalDateTime.now();
        if (this.status == null) this.status = "ISSUED";
    }

    // Manual Getters and Setters to ensure compatibility
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public Long getDeviceItemId() { return deviceItemId; }
    public void setDeviceItemId(Long deviceItemId) { this.deviceItemId = deviceItemId; }
    public LocalDateTime getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDateTime issuedDate) { this.issuedDate = issuedDate; }
    public LocalDateTime getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDateTime returnedDate) { this.returnedDate = returnedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}