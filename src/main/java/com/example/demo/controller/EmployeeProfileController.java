package com.example.demo.controller;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {

    private final EmployeeProfileService service;

    public EmployeeProfileController(EmployeeProfileService service) {
        this.service = service;
    }

    @PostMapping
    public EmployeeProfile createEmployee(@RequestBody EmployeeProfile employee) {
        return service.createEmployee(employee);
    }

    @GetMapping("/{id}")
    public EmployeeProfile getEmployee(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }

    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        service.updateEmployeeStatus(id, active);
    }
}
