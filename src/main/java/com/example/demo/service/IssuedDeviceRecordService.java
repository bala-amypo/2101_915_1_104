package com.example.demo.service;

import com.example.demo.model.IssuedDeviceRecord;
import java.util.List;

public interface IssuedDeviceRecordService {

    IssuedDeviceRecord issueDevice(Long employeeId, Long deviceItemId);

    IssuedDeviceRecord returnDevice(Long issuedRecordId);

    List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId);

    List<IssuedDeviceRecord> getAll();
}
