package com.example.demo.controller;

import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceCatalogController {

    private final DeviceCatalogService service;

    public DeviceCatalogController(DeviceCatalogService service) {
        this.service = service;
    }

    @PostMapping
    public DeviceCatalogItem createItem(@RequestBody DeviceCatalogItem item) {
        return service.createItem(item);
    }

    @PutMapping("/{id}/active")
    public void updateActive(@PathVariable Long id, @RequestParam boolean active) {
        service.updateActiveStatus(id, active);
    }

    @GetMapping
    public List<DeviceCatalogItem> getAllItems() {
        return service.getAllItems();
    }
}
