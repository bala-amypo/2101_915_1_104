package com.example.device.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(
    name = "device_catalog_item",
    uniqueConstraints = @UniqueConstraint(columnNames = "deviceCode")
)
public class DeviceCatalogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String deviceCode;

    @NotBlank
    private String deviceType;

    @NotBlank
    private String model;

    @Min(1)
    @Column(nullable = false)
    private Integer maxAllowedPerEmployee;

    @Column(nullable = false)
    private Boolean active = true;

    // Getters and Setters
}
