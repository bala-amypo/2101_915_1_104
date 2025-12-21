package com.example.demo.service.impl;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final List<IssuedDeviceRecord> store = new ArrayList<>();

    @Override
    public IssuedDeviceRecord create(IssuedDeviceRecord record) {
        store.add(record);
        return record;
    }

    @Override
    public List<IssuedDeviceRecord> getAll() {
        return store;
    }
}
