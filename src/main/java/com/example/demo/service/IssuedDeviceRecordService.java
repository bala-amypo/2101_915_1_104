import java.util.List;

public interface IssuedDeviceRecordService {

    IssuedDeviceRecord issueDevice(IssuedDeviceRecord record);

    void returnDevice(Long recordId);

    List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId);
}
