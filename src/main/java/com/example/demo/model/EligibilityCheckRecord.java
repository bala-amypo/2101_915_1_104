package com.example.eligibility.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "eligibility_check_record")
public class EligibilityCheckRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private Long deviceItemId;

    @Column(nullable = false)
    private Boolean isEligible;

    private String reason;

    @Column(nullable = false, updatable = false)
    private LocalDateTime checkedAt;

    @PrePersist
    protected void onCheck() {
        this.checkedAt = LocalDateTime.now();
    }

    // Getters and Setters
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

public Boolean getIsEligible() {
    return isEligible;
}

public void setIsEligible(Boolean isEligible) {
    this.isEligible = isEligible;
}

public String getReason() {
    return reason;
}

public void setReason(String reason) {
    this.reason = reason;
}

public LocalDateTime getCheckedAt() {
    return checkedAt;
}
}