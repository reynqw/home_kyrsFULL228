package com.smarthome.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// класс связи между пользователем и устройством (у пользователя может быть несколько устройств)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_devices")
@IdClass(UserDeviceId.class)
public class UserDevice {
    // id пользователя
    @Id
    @Column(name = "user_id")
    private Integer userId;

    // id устройства
    @Id
    @Column(name = "device_id")
    private Integer deviceId;

    // ссылка на пользователя
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    // ссылка на устройство
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", insertable = false, updatable = false)
    private Device device;

    // пользовательское значение устройства
    @Column(name = "value")
    private String value;

    // дата добавления устройства пользователем
    @Column(name = "added_at")
    private java.time.LocalDateTime addedAt;
} 