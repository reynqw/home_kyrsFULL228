package com.smarthome.controller;

import com.smarthome.entity.Device;
import com.smarthome.entity.User;
import com.smarthome.entity.UserDevice;
import com.smarthome.entity.UserRole;
import com.smarthome.repository.DeviceRepository;
import com.smarthome.repository.UserRepository;
import com.smarthome.repository.UserDeviceRepository;
import com.smarthome.dto.UserDeviceViewModel;
import com.smarthome.entity.DeviceTypeMeta;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final UserDeviceRepository userDeviceRepository;

    public DashboardController(DeviceRepository deviceRepository, UserRepository userRepository, UserDeviceRepository userDeviceRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.userDeviceRepository = userDeviceRepository;
    }

    // Smart mapping: determine DeviceTypeMeta by type and name
    private static DeviceTypeMeta resolveDeviceTypeMeta(Device device) {
        String type = device.getТип().toLowerCase();
        String name = device.getНазвание().toLowerCase();

        // Try by type and name for each device type
        if (type.contains("движ") || name.contains("движ") || type.contains("motion") || name.contains("motion") || type.contains("датчик движения") || name.contains("датчик движения") || type.contains("сенсор движения") || name.contains("сенсор движения")) {
            return DeviceTypeMeta.MOTION_SENSOR;
        }
        if (type.contains("свет") || name.contains("свет") || type.contains("реле") || name.contains("реле") || type.contains("переключатель света") || name.contains("переключатель света") || type.contains("выключатель") || name.contains("выключатель")) {
            return DeviceTypeMeta.LIGHT_SWITCH;
        }
        if (type.contains("отопл") || name.contains("отопл") || type.contains("обогрев") || name.contains("обогрев") || type.contains("heater") || name.contains("heater") || type.contains("переключатель") || name.contains("переключатель") || type.contains("выключатель отопления") || name.contains("выключатель отопления") || type.contains("переключатель отопления") || name.contains("переключатель отопления")) {
            return DeviceTypeMeta.HEATER_SWITCH;
        }
        if (type.contains("влажн") || name.contains("влажн") || type.contains("humidity") || name.contains("humidity") || type.contains("датчик влажности") || name.contains("датчик влажности")) {
            return DeviceTypeMeta.HUMIDITY_SENSOR;
        }
        if (type.contains("температур") || name.contains("температур") || type.contains("temperature") || name.contains("temperature") || type.contains("датчик температуры") || name.contains("датчик температуры")) {
            return DeviceTypeMeta.TEMPERATURE_SENSOR;
        }
        // Special case: type is 'сенсор' or 'датчик' and name indicates motion
        if ((type.equals("сенсор") || type.equals("датчик")) && (name.contains("движ") || name.contains("motion") || name.contains("датчик движения"))) {
            return DeviceTypeMeta.MOTION_SENSOR;
        }
        // Hardcoded fix: if the device name is 'Датчик движения у входа', force MOTION_SENSOR
        if (name.equals("датчик движения у входа")) {
            return DeviceTypeMeta.MOTION_SENSOR;
        }
        // fallback: try by type only
        DeviceTypeMeta metaByType = DeviceTypeMeta.fromType(type);
        if (metaByType != null) {
            return metaByType;
        }
        // fallback: try by name only
        DeviceTypeMeta metaByName = DeviceTypeMeta.fromType(name);
        if (metaByName != null) {
            return metaByName;
        }
        return null;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        
        // Get user's devices with eager loading
        List<UserDevice> userDevices = userDeviceRepository.findByUserIdWithDevice(user.getUserId());
        
        // Build view models with smart metadata
        List<UserDeviceViewModel> userDeviceVMs = userDevices.stream()
            .map(ud -> new UserDeviceViewModel(ud, resolveDeviceTypeMeta(ud.getDevice())))
            .collect(Collectors.toList());
        
        // If user is admin, get all devices
        if (user.getRole() == UserRole.ADMIN) {
            List<Device> allDevices = deviceRepository.findAll();
            model.addAttribute("allDevices", allDevices);
        }
        
        model.addAttribute("userDeviceVMs", userDeviceVMs);
        model.addAttribute("user", user);
        return "dashboard";
    }
} 