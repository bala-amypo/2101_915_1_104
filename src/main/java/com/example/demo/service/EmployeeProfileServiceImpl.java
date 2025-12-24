package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {
    
    private final EmployeeProfileRepository repository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeProfile createEmployee(EmployeeProfile employee) {
        if (repository.findByEmployeeId(employee.getEmployeeId()) != null) {
            throw new BadRequestException("EmployeeId already exists");
        }
        return repository.save(employee);
    }

    @Override
    public EmployeeProfile getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public List<EmployeeProfile> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public EmployeeProfile update(Long id, EmployeeProfile details) {
        EmployeeProfile emp = getEmployeeById(id);
        emp.setFullName(details.getFullName());
        emp.setDepartment(details.getDepartment());
        emp.setJobRole(details.getJobRole());
        emp.setEmail(details.getEmail());
        return repository.save(emp);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getEmployeeById(id));
    }

    @Override
    public EmployeeProfile updateEmployeeStatus(Long id, boolean active) {
        EmployeeProfile emp = getEmployeeById(id);
        emp.setActive(active);
        return repository.save(emp);
    }
}