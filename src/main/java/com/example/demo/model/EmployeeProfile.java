package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String employeeId;

    @NotBlank
    private String fullName;

    @NotBlank
    private String email;

    @NotBlank
    private String department;

    @NotBlank
    private String jobRole;

    @NotNull
    private Boolean active;
}
