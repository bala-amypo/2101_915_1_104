package com.example.demo.service.impl;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final List<EmployeeProfile> employees = new ArrayList<>();

    @Override
    public EmployeeProfile createEmployee(EmployeeProfile employee) {
        employees.add(employee);
        return employee;
    }

    @Override
    public EmployeeProfile getEmployeeById(Long id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<EmployeeProfile> getAllEmployees() {
        return employees;
    }

    @Override
    public void updateEmployeeStatus(Long id, boolean active) {
        employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .ifPresent(e -> e.setActive(active));
    }
}
