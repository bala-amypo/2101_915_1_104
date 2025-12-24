package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "device_catalog_items")
public class DeviceCatalogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String deviceName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer maxAllowedPerEmployee;

    private boolean active = true;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Default Constructor
    public DeviceCatalogItem() {
    }

    // Full Constructor
    public DeviceCatalogItem(String deviceName, String category, Integer maxAllowedPerEmployee, boolean active) {
        this.deviceName = deviceName;
        this.category = category;
        this.maxAllowedPerEmployee = maxAllowedPerEmployee;
        this.active = active;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getMaxAllowedPerEmployee() {
        return maxAllowedPerEmployee;
    }

    public void setMaxAllowedPerEmployee(Integer maxAllowedPerEmployee) {
        this.maxAllowedPerEmployee = maxAllowedPerEmployee;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}