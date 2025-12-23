package com.example.demo.controller;

import com.example.demo.model.DeviceCatalogItem;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @PostMapping
    public DeviceCatalogItem createDevice(@RequestBody DeviceCatalogItem device) {
        return device;
    }
}
