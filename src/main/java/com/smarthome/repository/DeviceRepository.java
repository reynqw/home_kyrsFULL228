package com.smarthome.repository;

import com.smarthome.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// репозиторий для работы с сущностью Device
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    // поиск устройств по типу
    List<Device> findByТип(String тип);
    // поиск устройств по состоянию
    List<Device> findByСостояние(String состояние);
    // поиск устройств по расположению
    List<Device> findByРасположение(String расположение);
    List<Device> findByПользователь_UserId(Integer userId);
}