package com.example.device.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity

public class IssuedDeviceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private Long deviceItemId;

    @Column(nullable = false)
    private LocalDate issuedDate;

    private LocalDate returnedDate;

    @Column(nullable = false)
    private String status; // ISSUED / RETURNED

    @PrePersist
    @PreUpdate
    private void updateStatus() {
        this.status = (returnedDate == null) ? "ISSUED" : "RETURNED";
    }

    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public Long getEmployeeId() {
    return employeeId;
}

public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
}

public Long getDeviceItemId() {
    return deviceItemId;
}

public void setDeviceItemId(Long deviceItemId) {
    this.deviceItemId = deviceItemId;
}

public LocalDate getIssuedDate() {
    return issuedDate;
}

public void setIssuedDate(LocalDate issuedDate) {
    this.issuedDate = issuedDate;
}

public LocalDate getReturnedDate() {
    return returnedDate;
}

public void setReturnedDate(LocalDate returnedDate) {
    this.returnedDate = returnedDate;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}
}