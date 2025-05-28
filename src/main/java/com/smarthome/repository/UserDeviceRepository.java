package com.smarthome.repository;

import com.smarthome.entity.UserDevice;
import com.smarthome.entity.UserDeviceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

// репозиторий для работы с сущностью UserDevice (связь пользователь-устройство)
public interface UserDeviceRepository extends JpaRepository<UserDevice, UserDeviceId> {
    // поиск всех устройств пользователя с подгрузкой устройства
    List<UserDevice> findByUserId(Integer userId);
    
    @Query("SELECT ud FROM UserDevice ud JOIN FETCH ud.device WHERE ud.userId = :userId")
    List<UserDevice> findByUserIdWithDevice(@Param("userId") Integer userId);
    
    Optional<UserDevice> findByUserIdAndDeviceId(Integer userId, Integer deviceId);
    
    @Query("SELECT ud FROM UserDevice ud JOIN FETCH ud.device WHERE ud.userId = :userId AND ud.deviceId = :deviceId")
    Optional<UserDevice> findByUserIdAndDeviceIdWithDevice(@Param("userId") Integer userId, @Param("deviceId") Integer deviceId);
    
    // удаление связи по id пользователя и id устройства
    void deleteByUserIdAndDeviceId(Integer userId, Integer deviceId);
} 