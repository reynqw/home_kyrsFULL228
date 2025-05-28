package com.smarthome.repository;

import com.smarthome.entity.ActionLog;
import com.smarthome.entity.ActionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

// репозиторий для работы с сущностью ActionLog (журнал действий)
public interface ActionLogRepository extends JpaRepository<ActionLog, Integer> {
    // поиск всех записей журнала по id пользователя
    List<ActionLog> findByПользователь_UserId(Integer userId);
    // поиск всех записей журнала по id устройства
    List<ActionLog> findByУстройство_Id(Integer deviceId);
    List<ActionLog> findByПравилоIdOrderByДатаВремяDesc(Integer ruleId);
    List<ActionLog> findByДатаВремяBetweenOrderByДатаВремяDesc(LocalDateTime start, LocalDateTime end);
    List<ActionLog> findByПользователь_UserIdOrderByДатаВремяDesc(Integer userId);
    
    // New paginated methods
    Page<ActionLog> findByПользователь_UserIdOrderByДатаВремяDesc(Integer userId, Pageable pageable);
    Page<ActionLog> findByПользователь_UserIdAndДатаВремяBetweenOrderByДатаВремяDesc(
        Integer userId, LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<ActionLog> findByПользователь_UserIdAndУстройствоIdOrderByДатаВремяDesc(
        Integer userId, Integer deviceId, Pageable pageable);
    Page<ActionLog> findByПользователь_UserIdAndПравилоIdOrderByДатаВремяDesc(
        Integer userId, Integer ruleId, Pageable pageable);
    Page<ActionLog> findByПользователь_UserIdAndТипДействияOrderByДатаВремяDesc(
        Integer userId, ActionType типДействия, Pageable pageable);
    // поиск всех записей журнала по id устройства с сортировкой по дате (убывание)
    List<ActionLog> findByУстройствоIdOrderByДатаВремяDesc(Integer deviceId);
} 