package com.example.demo.controller;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issued-devices")
public class IssuedDeviceRecordController {

    private final IssuedDeviceRecordService service;

    public IssuedDeviceRecordController(IssuedDeviceRecordService service) {
        this.service = service;
    }

    @PostMapping("/issue")
    public IssuedDeviceRecord issue(
            @RequestParam Long employeeId,
            @RequestParam Long deviceItemId
    ) {
        return service.issueDevice(employeeId, deviceItemId);
    }

    @PutMapping("/return/{id}")
    public IssuedDeviceRecord returnDevice(@PathVariable Long id) {
        return service.returnDevice(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<IssuedDeviceRecord> byEmployee(@PathVariable Long employeeId) {
        return service.getIssuedDevicesByEmployee(employeeId);
    }

    @GetMapping
    public List<IssuedDeviceRecord> all() {
        return service.getAll();
    }
}
