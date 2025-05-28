package com.smarthome.controller;

import com.smarthome.entity.User;
import com.smarthome.repository.UserRepository;
import com.smarthome.repository.DeviceRepository;
import com.smarthome.repository.ActionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private ActionLogRepository actionLogRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        var users = userRepository.findAll();
        var devices = deviceRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("devices", devices);
        return "admin/dashboard";
    }

    @GetMapping("/user/{id}")
    public String userDetails(@PathVariable Integer id, Model model) {
        var userOpt = userRepository.findByUserId(id);
        if (userOpt.isEmpty()) {
            return "redirect:/admin/dashboard";
        }
        var user = userOpt.get();
        model.addAttribute("user", user);
        model.addAttribute("devices", user.getDevices());
        model.addAttribute("logs", user.getLogs());
        return "admin/user_details";
    }
} 