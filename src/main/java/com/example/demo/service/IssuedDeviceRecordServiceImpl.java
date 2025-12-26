@Service
@Transactional
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository issuedRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;
    private final EligibilityCheckService eligibilityService;

    public IssuedDeviceRecordServiceImpl(
            IssuedDeviceRecordRepository issuedRepo,
            EmployeeProfileRepository employeeRepo,
            DeviceCatalogItemRepository deviceRepo,
            EligibilityCheckService eligibilityService) {

        this.issuedRepo = issuedRepo;
        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
        this.eligibilityService = eligibilityService;
    }

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {

        EligibilityCheckRecord check =
                eligibilityService.validateEligibility(record.getEmployeeId(), record.getDeviceItemId());

        if (!check.getIsEligible()) {
            throw new BadRequestException(check.getReason());
        }

        issuedRepo.findActiveByEmployeeAndDevice(
                record.getEmployeeId(), record.getDeviceItemId()
        ).ifPresent(r -> {
            throw new BadRequestException("Already issued");
        });

        record.setIssuedDate(LocalDate.now());
        record.setStatus("ISSUED");

        return issuedRepo.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {

        IssuedDeviceRecord record = issuedRepo.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        if (record.getReturnedDate() != null) {
            throw new BadRequestException("already returned");
        }

        record.setReturnedDate(LocalDate.now());
        record.setStatus("RETURNED");

        return issuedRepo.save(record);
    }

    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return issuedRepo.findByEmployeeId(employeeId);
    }
}
