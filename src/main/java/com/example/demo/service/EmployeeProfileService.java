package com.example.employee.service;

import java.util.List;
import com.example.employee.model.EmployeeProfile;

public interface EmployeeProfileService {

    EmployeeProfile createEmployee(EmployeeProfile employee);

    EmployeeProfile getEmployeeById(Long id);

    List<EmployeeProfile> getAllEmployees();

    void updateEmployeeStatus(Long id, boolean active);
}
