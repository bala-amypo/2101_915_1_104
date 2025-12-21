package com.example.demo.model;

import jakarta.persistence.*;

@Entity

public class DeviceCatalogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String deviceCode;

    private String deviceType;

    private String model;

    @Column(nullable = false)
    private Integer maxAllowedPerEmployee;

    @Column(nullable = false)
    private Boolean active = true;

    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getDeviceCode() {
    return deviceCode;
}

public void setDeviceCode(String deviceCode) {
    this.deviceCode = deviceCode;
}

public String getDeviceType() {
    return deviceType;
}

public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
}

public String getModel() {
    return model;
}

public void setModel(String model) {
    this.model = model;
}

public Integer getMaxAllowedPerEmployee() {
    return maxAllowedPerEmployee;
}

public void setMaxAllowedPerEmployee(Integer maxAllowedPerEmployee) {
    this.maxAllowedPerEmployee = maxAllowedPerEmployee;
}

public Boolean getActive() {
    return active;
}

public void setActive(Boolean active) {
    this.active = active;
}
}