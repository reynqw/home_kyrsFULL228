package com.smarthome.controller;

import com.smarthome.entity.ActionLog;
import com.smarthome.repository.ActionLogRepository;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/action-logs")
public class ActionLogController {

    private final ActionLogRepository actionLogRepository;

    public ActionLogController(ActionLogRepository actionLogRepository) {
        this.actionLogRepository = actionLogRepository;
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<ActionLog>> getActionLogsByDeviceId(@PathVariable Integer deviceId) {
        return ResponseEntity.ok(actionLogRepository.findByУстройствоIdOrderByДатаВремяDesc(deviceId));
    }

    @GetMapping("/rule/{ruleId}")
    public ResponseEntity<List<ActionLog>> getActionLogsByRuleId(@PathVariable Integer ruleId) {
        return ResponseEntity.ok(actionLogRepository.findByПравилоIdOrderByДатаВремяDesc(ruleId));
    }

    @GetMapping("/range")
    public ResponseEntity<List<ActionLog>> getActionLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(actionLogRepository.findByДатаВремяBetweenOrderByДатаВремяDesc(start, end));
    }

    @PostMapping
    public ResponseEntity<ActionLog> createActionLog(@Valid @RequestBody ActionLog actionLog) {
        return ResponseEntity.ok(actionLogRepository.save(actionLog));
    }
} 