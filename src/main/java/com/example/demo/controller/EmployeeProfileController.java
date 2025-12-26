package com.example.demo.controller;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    public EmployeeProfileController(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
    }

    @PostMapping
    public ResponseEntity<EmployeeProfile> create(@RequestBody EmployeeProfile employee) {
        return ResponseEntity.ok(employeeProfileService.createEmployee(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProfile> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeProfileService.getEmployeeById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EmployeeProfile> updateStatus(@PathVariable Long id,
                                                        @RequestParam boolean active) {
        return ResponseEntity.ok(employeeProfileService.updateEmployeeStatus(id, active));
    }
}
