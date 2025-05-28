package com.smarthome.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "данные_устройств")
public class DeviceData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_данных", columnDefinition = "INT")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_устройства", nullable = false)
    private Device устройство;

    @Column(name = "дата_время")
    private LocalDateTime датаВремя = LocalDateTime.now();

    @Column(nullable = false)
    private String значение;
} 