package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.service.DeviceCatalogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeviceCatalogServiceImpl implements DeviceCatalogService {

    private final DeviceCatalogItemRepository repository;

    public DeviceCatalogServiceImpl(DeviceCatalogItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeviceCatalogItem createItem(DeviceCatalogItem item) {
        if (repository.findByDeviceCode(item.getDeviceCode()) != null) {
            throw new BadRequestException("Device code already exists");
        }
        if (item.getMaxAllowedPerEmployee() < 1) {
            throw new BadRequestException("maxAllowedPerEmployee");
        }
        item.setActive(true);
        return repository.save(item);
    }

    @Override
    public DeviceCatalogItem updateActiveStatus(Long id, boolean active) {
        DeviceCatalogItem item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        item.setActive(active);
        return repository.save(item);
    }

    @Override
    public List<DeviceCatalogItem> getAllItems() {
        return repository.findAll();
    }
}
