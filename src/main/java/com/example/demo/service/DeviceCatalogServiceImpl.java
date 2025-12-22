package com.example.demo.service.impl;

import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceCatalogServiceImpl implements DeviceCatalogService {

    private final List<DeviceCatalogItem> items = new ArrayList<>();

    @Override
    public DeviceCatalogItem createItem(DeviceCatalogItem item) {
        items.add(item);
        return item;
    }

    @Override
    public void updateActiveStatus(Long id, boolean active) {
        items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .ifPresent(i -> i.setActive(active));
    }

    @Override
    public List<DeviceCatalogItem> getAllItems() {
        return items;
    }
}
