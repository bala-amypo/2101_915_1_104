package com.example.device.service;

import java.util.List;
import com.example.device.model.IssuedDeviceRecord;

public interface IssuedDeviceRecordService {

    IssuedDeviceRecord issueDevice(IssuedDeviceRecord record);

    void returnDevice(Long recordId);

    List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId);
}
