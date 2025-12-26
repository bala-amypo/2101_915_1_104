package com.example.demo.controller;

import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceCatalogController {

    private final DeviceCatalogService deviceCatalogService;

    public DeviceCatalogController(DeviceCatalogService deviceCatalogService) {
        this.deviceCatalogService = deviceCatalogService;
    }

    @PostMapping
    public ResponseEntity<DeviceCatalogItem> create(@RequestBody DeviceCatalogItem item) {
        return ResponseEntity.ok(deviceCatalogService.createItem(item));
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<DeviceCatalogItem> updateActive(@PathVariable Long id,
                                                          @RequestParam boolean active) {
        return ResponseEntity.ok(deviceCatalogService.updateActiveStatus(id, active));
    }

    @GetMapping
    public ResponseEntity<List<DeviceCatalogItem>> getAll() {
        return ResponseEntity.ok(deviceCatalogService.getAllItems());
    }
}
