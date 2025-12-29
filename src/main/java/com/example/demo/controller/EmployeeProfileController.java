package com.example.demo.controller;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {

    private final EmployeeProfileService service;

    public EmployeeProfileController(EmployeeProfileService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public EmployeeProfile create( @RequestBody EmployeeProfile employee) {
        return service.createEmployee(employee); // ✅ FIXED
    }

    // UPDATE STATUS
    @PutMapping("/{id}/status")
    public EmployeeProfile updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        return service.updateEmployeeStatus(id, active);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public EmployeeProfile getById(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }

    // GET ALL
    @GetMapping
    public List<EmployeeProfile> getAll() {
        return service.getAllEmployees();
    }
}
