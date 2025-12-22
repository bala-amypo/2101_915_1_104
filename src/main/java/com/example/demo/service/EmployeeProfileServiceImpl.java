package com.example.demo.service.impl;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final List<EmployeeProfile> store = new ArrayList<>();

    @Override
    public EmployeeProfile create(EmployeeProfile employee) {
        store.add(employee);
        return employee;
    }

    @Override
    public EmployeeProfile getById(Long id) {
        return store.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<EmployeeProfile> getAll() {
        return store;
    }

    @Override
    public EmployeeProfile update(Long id, EmployeeProfile employee) {
        delete(id);
        store.add(employee);
        return employee;
    }

    @Override
    public void delete(Long id) {
        store.removeIf(e -> e.getId().equals(id));
    }
}
