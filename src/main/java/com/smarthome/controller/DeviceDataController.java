package com.smarthome.controller;

import com.smarthome.entity.DeviceData;
import com.smarthome.repository.DeviceDataRepository;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/device-data")
public class DeviceDataController {

    private final DeviceDataRepository deviceDataRepository;

    public DeviceDataController(DeviceDataRepository deviceDataRepository) {
        this.deviceDataRepository = deviceDataRepository;
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<DeviceData>> getDeviceDataByDeviceId(@PathVariable Integer deviceId) {
        return ResponseEntity.ok(deviceDataRepository.findByУстройствоIdOrderByДатаВремяDesc(deviceId));
    }

    @GetMapping("/device/{deviceId}/range")
    public ResponseEntity<List<DeviceData>> getDeviceDataByDateRange(
            @PathVariable Integer deviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(deviceDataRepository.findByУстройствоIdAndДатаВремяBetweenOrderByДатаВремяDesc(
                deviceId, start, end));
    }

    @PostMapping
    public ResponseEntity<DeviceData> createDeviceData(@Valid @RequestBody DeviceData deviceData) {
        return ResponseEntity.ok(deviceDataRepository.save(deviceData));
    }
} 