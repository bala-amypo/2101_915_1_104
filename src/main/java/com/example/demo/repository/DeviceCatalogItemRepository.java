package com.example.demo.repository;

import com.example.demo.model.DeviceCatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceCatalogItemRepository extends JpaRepository<DeviceCatalogItem, Long> {

    Optional<DeviceCatalogItem> findByDeviceCode(String deviceCode);
}
