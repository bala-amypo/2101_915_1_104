package com.example.demo.controller;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issued-devices")
public class IssuedDeviceRecordController {

    private final IssuedDeviceRecordService issuedDeviceRecordService;

    public IssuedDeviceRecordController(IssuedDeviceRecordService issuedDeviceRecordService) {
        this.issuedDeviceRecordService = issuedDeviceRecordService;
    }

    @PostMapping
    public ResponseEntity<IssuedDeviceRecord> issue(@RequestBody IssuedDeviceRecord record) {
        return ResponseEntity.ok(issuedDeviceRecordService.issueDevice(record));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<IssuedDeviceRecord> returnDevice(@PathVariable Long id) {
        return ResponseEntity.ok(issuedDeviceRecordService.returnDevice(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<IssuedDeviceRecord>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(
                issuedDeviceRecordService.getIssuedDevicesByEmployee(employeeId)
        );
    }
}
