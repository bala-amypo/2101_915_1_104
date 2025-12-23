package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DeviceController {

    @PostMapping("/devices")
    public ResponseEntity<DeviceRequest> createDevice(@RequestBody DeviceRequest request) {
        // later you can save to DB; for now just echo back
        return ResponseEntity.ok(request);
    }

    public static class DeviceRequest {
        private Long id;
        private String deviceCode;
        private String deviceType;
        private String model;
        private Integer maxAllowedPerEmployee;
        private Boolean active;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getDeviceCode() { return deviceCode; }
        public void setDeviceCode(String deviceCode) { this.deviceCode = deviceCode; }

        public String getDeviceType() { return deviceType; }
        public void setDeviceType(String deviceType) { this.deviceType = deviceType; }

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }

        public Integer getMaxAllowedPerEmployee() { return maxAllowedPerEmployee; }
        public void setMaxAllowedPerEmployee(Integer maxAllowedPerEmployee) { this.maxAllowedPerEmployee = maxAllowedPerEmployee; }

        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
    }
}
