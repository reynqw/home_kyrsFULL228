package com.smarthome.controller;

import com.smarthome.entity.Device;
import com.smarthome.entity.Rule;
import com.smarthome.entity.User;
import com.smarthome.entity.UserDevice;
import com.smarthome.repository.DeviceRepository;
import com.smarthome.repository.RuleRepository;
import com.smarthome.repository.UserDeviceRepository;
import com.smarthome.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/rules")
@Controller
public class RuleController {

    private final RuleRepository ruleRepository;
    private final UserRepository userRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final DeviceRepository deviceRepository;

    public RuleController(RuleRepository ruleRepository, UserRepository userRepository, UserDeviceRepository userDeviceRepository, DeviceRepository deviceRepository) {
        this.ruleRepository = ruleRepository;
        this.userRepository = userRepository;
        this.userDeviceRepository = userDeviceRepository;
        this.deviceRepository = deviceRepository;
    }

    @GetMapping("/customize")
    public String customizeRulesPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        List<UserDevice> userDevices = userDeviceRepository.findByUserIdWithDevice(user.getUserId());
        Map<Device, List<Rule>> deviceRules = new LinkedHashMap<>();

        // Fetch rules with actions
        List<Rule> rulesWithActions = ruleRepository.findAllWithActions();
        // Fetch rules with conditions
        List<Rule> rulesWithConditions = ruleRepository.findAllWithConditions();

        // Merge by rule ID
        Map<Integer, Rule> ruleMap = new HashMap<>();
        for (Rule rule : rulesWithActions) {
            ruleMap.put(rule.getId(), rule);
        }
        for (Rule rule : rulesWithConditions) {
            Rule r = ruleMap.get(rule.getId());
            if (r != null && rule.getУсловия() != null) {
                r.setУсловия(rule.getУсловия());
            }
        }
        List<Rule> rules = new ArrayList<>(ruleMap.values());

        // Группируем правила по устройствам
        for (UserDevice ud : userDevices) {
            Integer deviceId = ud.getDevice().getId();
            List<Rule> filteredRules = rules.stream()
                .filter(r ->
                    (r.getДействия() != null && r.getДействия().stream().anyMatch(a -> a.getУстройство().getId().equals(deviceId))) ||
                    (r.getУсловия() != null && r.getУсловия().stream().anyMatch(c -> c.getУстройство().getId().equals(deviceId)))
                )
                .toList();
            // Инициализация коллекций для избежания LazyInitializationException
            for (Rule rule : filteredRules) {
                rule.getДействия().size();
                rule.getУсловия().size();
            }
            deviceRules.put(ud.getDevice(), filteredRules);
        }
        model.addAttribute("deviceRules", deviceRules);
        return "rules_customize";
    }

    @GetMapping("")
    public String rulesPage(Model model) {
        List<Rule> rules = ruleRepository.findAll();
        // Initialize collections to avoid LazyInitializationException
        for (Rule rule : rules) {
            rule.getУсловия().size();
            rule.getДействия().size();
        }
        model.addAttribute("rules", rules);
        return "rules";
    }
} 