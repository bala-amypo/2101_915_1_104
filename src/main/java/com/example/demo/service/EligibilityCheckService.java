import java.util.List;

public interface EligibilityCheckService {

    boolean validateEligibility(Long employeeId, Long deviceItemId);

    List<EligibilityCheck> getChecksByEmployee(Long employeeId);
}
