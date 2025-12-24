package com.example.demo.service;

import com.example.demo.model.EmployeeProfile;
import java.util.List;

public interface EmployeeProfileService {
    EmployeeProfile createEmployee(EmployeeProfile employee);
    EmployeeProfile getEmployeeById(Long id);
    List<EmployeeProfile> getAllEmployees();
    void delete(Long id);
    EmployeeProfile updateEmployeeStatus(Long id, boolean active);
    // This was missing and causing the "not abstract" error
    EmployeeProfile update(Long id, EmployeeProfile employee);
}