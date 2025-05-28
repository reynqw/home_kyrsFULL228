package com.smarthome.controller;

import com.smarthome.entity.Device;
import com.smarthome.entity.User;
import com.smarthome.entity.UserDevice;
import com.smarthome.entity.UserDeviceId;
import com.smarthome.entity.DeviceTypeMeta;
import com.smarthome.repository.DeviceRepository;
import com.smarthome.repository.UserDeviceRepository;
import com.smarthome.repository.UserRepository;
import com.smarthome.repository.ActionLogRepository;
import com.smarthome.entity.ActionLog;
import com.smarthome.entity.ActionType;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// контроллер для работы с устройствами пользователя и их значениями
@Controller
public class DeviceController {

    // внедряем необходимые репозитории через конструктор
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);
    private final DeviceRepository deviceRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final UserRepository userRepository;
    private final ActionLogRepository actionLogRepository;

    public DeviceController(DeviceRepository deviceRepository, UserDeviceRepository userDeviceRepository, UserRepository userRepository, ActionLogRepository actionLogRepository) {
        this.deviceRepository = deviceRepository;
        this.userDeviceRepository = userDeviceRepository;
        this.userRepository = userRepository;
        this.actionLogRepository = actionLogRepository;
    }

    // отображение страницы со всеми устройствами и отмеченными устройствами пользователя
    @GetMapping("/devices")
    public String devicesPage(Model model, Principal principal) {
        List<Device> devices = deviceRepository.findAll(); // получаем все устройства
        User user = userRepository.findByUsername(principal.getName()).orElse(null); // получаем текущего пользователя
        List<UserDevice> userDevices = userDeviceRepository.findByUserIdWithDevice(user.getUserId()); // устройства пользователя
        Set<Integer> userDeviceIds = userDevices.stream().map(UserDevice::getDeviceId).collect(Collectors.toSet()); // id устройств пользователя
        model.addAttribute("devices", devices);
        model.addAttribute("userDeviceIds", userDeviceIds);
        return "devices";
    }

    // добавление устройства пользователю
    @PostMapping("/devices/add/{deviceId}")
    public String addDeviceToUser(@PathVariable Integer deviceId, Principal principal) {
        logger.info("POST /devices/add/{} called", deviceId);
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user != null && deviceRepository.existsById(deviceId)) {
            // если у пользователя ещё нет этого устройства, добавляем
            userDeviceRepository.findByUserIdAndDeviceIdWithDevice(user.getUserId(), deviceId)
                .orElseGet(() -> {
                    Device device = deviceRepository.findById(deviceId).orElse(null);
                    UserDevice ud = new UserDevice();
                    ud.setUserId(user.getUserId());
                    ud.setDeviceId(deviceId);
                    ud.setUser(user);
                    ud.setDevice(device);
                    ud.setValue(""); // значение по умолчанию
                    ud.setAddedAt(LocalDateTime.now()); // дата добавления
                    userDeviceRepository.save(ud);
                    // логируем добавление устройства
                    ActionLog log = new ActionLog();
                    log.setПользователь(user);
                    log.setУстройство(device);
                    log.setДатаВремя(LocalDateTime.now());
                    log.setВыполнено("Добавлено устройство");
                    log.setРезультат("Успешно");
                    log.setЗначениеУстройства(ud.getValue());
                    log.setТипДействия(ActionType.SET);
                    log.setПравило(null);
                    actionLogRepository.save(log);
                    // конец логирования добавления
                    return ud;
                });
        }
        return "redirect:/devices";
    }

    // удаление устройства у пользователя
    @PostMapping("/devices/remove/{deviceId}")
    @Transactional
    public String removeDeviceFromUser(@PathVariable Integer deviceId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user != null) {
            userDeviceRepository.findByUserIdAndDeviceIdWithDevice(user.getUserId(), deviceId)
                .ifPresent(ud -> userDeviceRepository.deleteByUserIdAndDeviceId(user.getUserId(), deviceId));
        }
        return "redirect:/devices";
    }

    // изменение значения устройства пользователем
    @PostMapping("/devices/user-value/{deviceId}")
    public String updateUserDeviceValue(@PathVariable Integer deviceId, @RequestParam String value, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user != null) {
            Optional<UserDevice> userDeviceOpt = userDeviceRepository.findByUserIdAndDeviceIdWithDevice(user.getUserId(), deviceId);
            if (userDeviceOpt.isPresent()) {
                UserDevice userDevice = userDeviceOpt.get();
                String deviceType = userDevice.getDevice().getТип();
                String deviceName = userDevice.getDevice().getНазвание();
                String type = deviceType.toLowerCase();
                String name = deviceName.toLowerCase();

                // определяем мета-тип устройства для валидации значения
                DeviceTypeMeta meta = null;
                // особый случай: сенсор движения
                if ((type.equals("сенсор") || type.equals("датчик")) && (name.contains("движ") || name.contains("motion") || name.contains("датчик движения"))) {
                    meta = DeviceTypeMeta.MOTION_SENSOR;
                }
                // жёстко заданный случай для конкретного имени
                if (meta == null && name.equals("датчик движения у входа")) {
                    meta = DeviceTypeMeta.MOTION_SENSOR;
                }
                if (meta == null) {
                    if (type.contains("движ") || name.contains("движ") || type.contains("motion") || name.contains("motion") || type.contains("датчик движения") || name.contains("датчик движения") || type.contains("сенсор движения") || name.contains("сенсор движения")) {
                        meta = DeviceTypeMeta.MOTION_SENSOR;
                    } else if (type.contains("свет") || name.contains("свет") || type.contains("реле") || name.contains("реле") || type.contains("переключатель света") || name.contains("переключатель света") || type.contains("выключатель") || name.contains("выключатель")) {
                        meta = DeviceTypeMeta.LIGHT_SWITCH;
                    } else if (type.contains("отопл") || name.contains("отопл") || type.contains("обогрев") || name.contains("обогрев") || type.contains("heater") || name.contains("heater") || type.contains("переключатель") || name.contains("переключатель") || type.contains("выключатель отопления") || name.contains("выключатель отопления") || type.contains("переключатель отопления") || name.contains("переключатель отопления")) {
                        meta = DeviceTypeMeta.HEATER_SWITCH;
                    } else if (type.contains("влажн") || name.contains("влажн") || type.contains("humidity") || name.contains("humidity") || type.contains("датчик влажности") || name.contains("датчик влажности")) {
                        meta = DeviceTypeMeta.HUMIDITY_SENSOR;
                    } else if (type.contains("температур") || name.contains("температур") || type.contains("temperature") || name.contains("temperature") || type.contains("датчик температуры") || name.contains("датчик температуры")) {
                        meta = DeviceTypeMeta.TEMPERATURE_SENSOR;
                    }
                }
                if (meta == null) {
                    meta = DeviceTypeMeta.fromType(type);
                }
                if (meta == null) {
                    meta = DeviceTypeMeta.fromType(name);
                }

                boolean valid = false;
                String errorMessage = "Недопустимое значение для устройства.";

                if (meta != null) {
                    if (meta.getAllowedValues() != null) {
                        // для устройств с перечисляемыми значениями (например, переключатели)
                        for (String allowed : meta.getAllowedValues()) {
                            if (allowed.trim().equalsIgnoreCase(value.trim())) {
                                valid = true;
                                break;
                            }
                        }
                        if (!valid) {
                            errorMessage = "Допустимые значения: " + String.join(", ", meta.getAllowedValues());
                        }
                    } else if (meta.getMinValue() != null && meta.getMaxValue() != null) {
                        // для устройств с диапазоном значений (например, датчики)
                        try {
                            double doubleValue = Double.parseDouble(value);
                            if (doubleValue >= meta.getMinValue() && doubleValue <= meta.getMaxValue()) {
                                valid = true;
                            } else {
                                errorMessage = String.format("Значение должно быть между %d и %d", meta.getMinValue(), meta.getMaxValue());
                                if (meta == DeviceTypeMeta.HUMIDITY_SENSOR) {
                                    errorMessage += " (проценты влажности)";
                                } else if (meta == DeviceTypeMeta.TEMPERATURE_SENSOR) {
                                    errorMessage += " (градусы Цельсия)";
                                }
                            }
                        } catch (NumberFormatException e) {
                            errorMessage = "Значение должно быть числом";
                        }
                    }
                } else {
                    errorMessage = "Неизвестный тип устройства: " + deviceType;
                }

                if (valid) {
                    userDevice.setValue(value); // сохраняем новое значение
                    userDeviceRepository.save(userDevice);

                    // логируем действие изменения значения устройства
                    ActionLog log = new ActionLog();
                    log.setПользователь(user);
                    log.setУстройство(userDevice.getDevice());
                    log.setДатаВремя(LocalDateTime.now());
                    log.setВыполнено("Изменено значение устройства");
                    log.setРезультат("Успешно");
                    log.setЗначениеУстройства(value);
                    log.setТипДействия(ActionType.SET);
                    log.setПравило(null);
                    actionLogRepository.save(log);
                    // конец логирования действия

                    redirectAttributes.addFlashAttribute("success", "Значение успешно установлено!");
                } else {
                    redirectAttributes.addFlashAttribute("error", errorMessage);
                }
            }
        }
        return "redirect:/dashboard";
    }

    // api для получения всех устройств
    @GetMapping("/api/devices")
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(deviceRepository.findAll());
    }

    // api для получения устройства по id
    @GetMapping("/api/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Integer id) {
        return deviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // api для получения устройств по типу
    @GetMapping("/api/devices/type/{type}")
    public ResponseEntity<List<Device>> getDevicesByType(@PathVariable String type) {
        return ResponseEntity.ok(deviceRepository.findByТип(type));
    }

    // api для получения устройств по состоянию
    @GetMapping("/api/devices/state/{state}")
    public ResponseEntity<List<Device>> getDevicesByState(@PathVariable String state) {
        return ResponseEntity.ok(deviceRepository.findByСостояние(state));
    }

    // api для получения устройств по расположению
    @GetMapping("/api/devices/location/{location}")
    public ResponseEntity<List<Device>> getDevicesByLocation(@PathVariable String location) {
        return ResponseEntity.ok(deviceRepository.findByРасположение(location));
    }

    // api для создания нового устройства (только для админа)
    @PostMapping("/api/devices")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        return ResponseEntity.ok(deviceRepository.save(device));
    }

    // api для обновления устройства (только для админа)
    @PutMapping("/api/devices/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Device> updateDevice(@PathVariable Integer id, @Valid @RequestBody Device device) {
        return deviceRepository.findById(id)
                .map(existingDevice -> {
                    device.setId(id);
                    return ResponseEntity.ok(deviceRepository.save(device));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // api для удаления устройства (только для админа)
    @DeleteMapping("/api/devices/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDevice(@PathVariable Integer id) {
        return deviceRepository.findById(id)
                .map(device -> {
                    deviceRepository.delete(device);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // обработка get-запроса на добавление устройства (редиректит на список устройств)
    @GetMapping("/devices/add/{deviceId}")
    public String addDeviceToUserGet(@PathVariable Integer deviceId) {
        logger.info("GET /devices/add/{} called", deviceId);
        return "redirect:/devices";
    }
} 