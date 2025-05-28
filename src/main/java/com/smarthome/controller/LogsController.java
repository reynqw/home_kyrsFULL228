package com.smarthome.controller;

import com.smarthome.entity.ActionLog;
import com.smarthome.repository.ActionLogRepository;
import com.smarthome.repository.UserRepository;
import com.smarthome.entity.User;
import com.smarthome.entity.ActionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.security.Principal;

@Controller
public class LogsController {
    private final ActionLogRepository actionLogRepository;
    private final UserRepository userRepository;

    public LogsController(ActionLogRepository actionLogRepository, UserRepository userRepository) {
        this.actionLogRepository = actionLogRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/logs")
    public String logsPage(
            Model model,
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Integer deviceId,
            @RequestParam(required = false) Integer ruleId,
            @RequestParam(required = false) ActionType actionType,
            @RequestParam(defaultValue = "датаВремя") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        Page<ActionLog> logs;
        if (startDate != null && endDate != null) {
            logs = actionLogRepository.findByПользователь_UserIdAndДатаВремяBetweenOrderByДатаВремяDesc(
                user.getUserId(), startDate, endDate, pageRequest);
        } else if (deviceId != null) {
            logs = actionLogRepository.findByПользователь_UserIdAndУстройствоIdOrderByДатаВремяDesc(
                user.getUserId(), deviceId, pageRequest);
        } else if (ruleId != null) {
            logs = actionLogRepository.findByПользователь_UserIdAndПравилоIdOrderByДатаВремяDesc(
                user.getUserId(), ruleId, pageRequest);
        } else if (actionType != null) {
            logs = actionLogRepository.findByПользователь_UserIdAndТипДействияOrderByДатаВремяDesc(
                user.getUserId(), actionType, pageRequest);
        } else {
            logs = actionLogRepository.findByПользователь_UserIdOrderByДатаВремяDesc(
                user.getUserId(), pageRequest);
        }
        
        model.addAttribute("logs", logs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", logs.getTotalPages());
        model.addAttribute("totalItems", logs.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("deviceId", deviceId);
        model.addAttribute("ruleId", ruleId);
        model.addAttribute("actionType", actionType);
        model.addAttribute("actionTypes", ActionType.values());
        
        return "logs";
    }
} 