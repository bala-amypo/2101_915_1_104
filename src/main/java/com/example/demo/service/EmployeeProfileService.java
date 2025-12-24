package com.example.demo.service;

import com.example.demo.model.EmployeeProfile;
import java.util.List;

public interface EmployeeProfileService {
    EmployeeProfile createEmployee(EmployeeProfile employee);
    EmployeeProfile getEmployeeById(Long id);
    List<EmployeeProfile> getAllEmployees();
    void delete(Long id);
    EmployeeProfile updateEmployeeStatus(Long id, boolean active);
    // This MUST exist to satisfy the compiler
    EmployeeProfile update(Long id, EmployeeProfile employee);
}