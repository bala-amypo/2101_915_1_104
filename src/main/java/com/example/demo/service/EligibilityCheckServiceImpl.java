@Service
@Transactional
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;
    private final IssuedDeviceRecordRepository issuedRepo;
    private final PolicyRuleRepository policyRepo;
    private final EligibilityCheckRecordRepository recordRepo;

    public EligibilityCheckServiceImpl(
            EmployeeProfileRepository employeeRepo,
            DeviceCatalogItemRepository deviceRepo,
            IssuedDeviceRecordRepository issuedRepo,
            PolicyRuleRepository policyRepo,
            EligibilityCheckRecordRepository recordRepo) {

        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
        this.issuedRepo = issuedRepo;
        this.policyRepo = policyRepo;
        this.recordRepo = recordRepo;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {

        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployeeId(employeeId);
        record.setDeviceItemId(deviceItemId);

        EmployeeProfile employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (!employee.getActive()) {
            record.setIsEligible(false);
            record.setReason("Employee inactive");
            return recordRepo.save(record);
        }

        DeviceCatalogItem device = deviceRepo.findById(deviceItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        long activeCount = issuedRepo.countActiveDevicesForEmployee(employeeId);
        if (activeCount >= device.getMaxAllowedPerEmployee()) {
            record.setIsEligible(false);
            record.setReason("Device limit exceeded");
            return recordRepo.save(record);
        }

        record.setIsEligible(true);
        record.setReason("Eligible");
        return recordRepo.save(record);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return recordRepo.findByEmployeeId(employeeId);
    }
}
