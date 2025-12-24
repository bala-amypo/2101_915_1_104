package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeProfileServiceImpl implements EmployeeProfileService {
    
    private final EmployeeProfileRepository employeeRepository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeProfile createEmployee(EmployeeProfile employee) {
        if (employeeRepository.findByEmployeeId(employee.getEmployeeId()) != null) {
            throw new BadRequestException("EmployeeId already exists");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeProfile getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public List<EmployeeProfile> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        EmployeeProfile emp = getEmployeeById(id);
        employeeRepository.delete(emp);
    }

    @Override
    public EmployeeProfile updateEmployeeStatus(Long id, boolean active) {
        EmployeeProfile emp = getEmployeeById(id);
        emp.setActive(active);
        return employeeRepository.save(emp);
    }

    @Override
    public EmployeeProfile update(Long id, EmployeeProfile details) {
        EmployeeProfile emp = getEmployeeById(id);
        emp.setFullName(details.getFullName());
        emp.setDepartment(details.getDepartment());
        emp.setJobRole(details.getJobRole());
        emp.setEmail(details.getEmail());
        return employeeRepository.save(emp);
    }
}