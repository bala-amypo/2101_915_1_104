package com.example.demo;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.*;
import com.example.demo.service.impl.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * TestNG + Mockito based tests
 * IMPORTANT: NO @SpringBootTest here
 */
public class EmployeeEquipmentIssuancePolicyCheckerTests {

    @Mock
    private EmployeeProfileRepository employeeRepo;
    @Mock
    private DeviceCatalogItemRepository deviceRepo;
    @Mock
    private IssuedDeviceRecordRepository issuedRepo;
    @Mock
    private PolicyRuleRepository policyRepo;
    @Mock
    private EligibilityCheckRecordRepository eligibilityRepo;

    private EmployeeProfileService employeeService;
    private DeviceCatalogService deviceService;
    private IssuedDeviceRecordService issuedService;
    private PolicyRuleService ruleService;
    private EligibilityCheckService eligibilityService;

    private JwtTokenProvider tokenProvider;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.openMocks(this);

        employeeService = new EmployeeProfileServiceImpl(employeeRepo);
        deviceService = new DeviceCatalogServiceImpl(deviceRepo);
        issuedService = new IssuedDeviceRecordServiceImpl(
                issuedRepo, employeeRepo, deviceRepo
        );
        ruleService = new PolicyRuleServiceImpl(policyRepo);
        eligibilityService = new EligibilityCheckServiceImpl(
                employeeRepo, deviceRepo, issuedRepo, policyRepo, eligibilityRepo
        );

        tokenProvider = new JwtTokenProvider(
                "ChangeThisSecretKeyForJwt123456789012345",
                3600000
        );
    }

    // ---------------- BASIC TESTS ----------------

    @Test
    public void testEmployeeCreationSuccess() {
        EmployeeProfile emp = new EmployeeProfile();
        emp.setEmployeeId("E001");
        emp.setEmail("test@example.com");
        emp.setActive(true);
        emp.setCreatedAt(LocalDateTime.now());

        when(employeeRepo.findByEmployeeId("E001"))
                .thenReturn(Optional.empty());
        when(employeeRepo.findByEmail("test@example.com"))
                .thenReturn(Optional.empty());
        when(employeeRepo.save(any(EmployeeProfile.class)))
                .thenReturn(emp);

        EmployeeProfile saved = employeeService.createEmployee(emp);
        Assert.assertEquals(saved.getEmployeeId(), "E001");
    }

    @Test
    public void testJwtTokenGenerationAndValidation() {
        UserAccount user = new UserAccount();
        user.setId(1L);
        user.setEmail("admin@test.com");
        user.setRole("ADMIN");

        String token = tokenProvider.generateToken(user);
        Assert.assertTrue(tokenProvider.validateToken(token));
        Assert.assertEquals(tokenProvider.getUsername(token), "admin@test.com");
    }

    @Test
    public void testEligibilityEmployeeNotFound() {
        when(employeeRepo.findById(1L)).thenReturn(Optional.empty());
        when(deviceRepo.findById(1L)).thenReturn(Optional.empty());
        when(eligibilityRepo.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        EligibilityCheckRecord rec =
                eligibilityService.validateEligibility(1L, 1L);

        Assert.assertFalse(rec.getIsEligible());
        Assert.assertTrue(rec.getReason().contains("not found"));
    }

    @Test
    public void testEligibilityPositiveCase() {
        EmployeeProfile emp = new EmployeeProfile();
        emp.setId(1L);
        emp.setActive(true);

        DeviceCatalogItem dev = new DeviceCatalogItem();
        dev.setId(1L);
        dev.setActive(true);
        dev.setMaxAllowedPerEmployee(2);

        when(employeeRepo.findById(1L)).thenReturn(Optional.of(emp));
        when(deviceRepo.findById(1L)).thenReturn(Optional.of(dev));
        when(issuedRepo.findActiveByEmployeeAndDevice(1L, 1L))
                .thenReturn(Collections.emptyList());
        when(issuedRepo.countActiveDevicesForEmployee(1L))
                .thenReturn(0L);
        when(policyRepo.findByActiveTrue())
                .thenReturn(Collections.emptyList());
        when(eligibilityRepo.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        EligibilityCheckRecord rec =
                eligibilityService.validateEligibility(1L, 1L);

        Assert.assertTrue(rec.getIsEligible());
    }
}
