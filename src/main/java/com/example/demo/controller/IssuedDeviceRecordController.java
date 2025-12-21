package com.example.device.controller;

import com.example.device.model.IssuedDeviceRecord;
import com.example.device.service.IssuedDeviceRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issued-devices")
public class IssuedDeviceRecordController {

    private final IssuedDeviceRecordService service;

    public IssuedDeviceRecordController(IssuedDeviceRecordService service) {
        this.service = service;
    }

    @PostMapping
    public IssuedDeviceRecord issueDevice(@RequestBody IssuedDeviceRecord record) {
        return service.issueDevice(record);
    }

    @PutMapping("/{id}/return")
    public void returnDevice(@PathVariable Long id) {
        service.returnDevice(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<IssuedDeviceRecord> getByEmployee(@PathVariable Long employeeId) {
        return service.getIssuedDevicesByEmployee(employeeId);
    }
}
