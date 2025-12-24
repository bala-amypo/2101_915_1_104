package com.example.demo.controller;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {
    private final EmployeeProfileService service;

    public EmployeeProfileController(EmployeeProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EmployeeProfile> create(@RequestBody EmployeeProfile employee) {
        return ResponseEntity.ok(service.createEmployee(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProfile> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEmployeeById(id));
    }
}