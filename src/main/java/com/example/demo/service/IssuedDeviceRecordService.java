package com.example.demo.service;

import com.example.demo.model.IssuedDeviceRecord;
import java.util.List;

public interface IssuedDeviceRecordService {

    IssuedDeviceRecord create(IssuedDeviceRecord record);

    List<IssuedDeviceRecord> getAll();
}
