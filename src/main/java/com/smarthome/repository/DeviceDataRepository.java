package com.smarthome.repository;

import com.smarthome.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

// репозиторий для работы с сущностью DeviceData (история данных устройств)
public interface DeviceDataRepository extends JpaRepository<DeviceData, Integer> {
    // поиск всех данных устройства по id устройства
    List<DeviceData> findByУстройствоIdOrderByДатаВремяDesc(Integer deviceId);
    List<DeviceData> findByУстройствоIdAndДатаВремяBetweenOrderByДатаВремяDesc(
        Integer deviceId, LocalDateTime start, LocalDateTime end);
}
