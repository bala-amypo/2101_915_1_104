package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeProfile createEmployee(EmployeeProfile employee) {

        if (repository.findByEmployeeId(employee.getEmployeeId()).isPresent()) {
            throw new BadRequestException("EmployeeId already exists");
        }

        if (repository.findByEmail(employee.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        employee.setCreatedAt(LocalDateTime.now());
        employee.setActive(true);

        return repository.save(employee);
    }

    @Override
    public EmployeeProfile updateEmployeeStatus(Long id, boolean active) {
        EmployeeProfile emp = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        emp.setActive(active);
        return repository.save(emp);
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
}
