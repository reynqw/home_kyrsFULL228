package com.smarthome.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// класс для хранения действий, которые могут выполняться по правилам автоматизации
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "действия")
public class Action {
    // id действия
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_действия")
    private Integer id;

    // правило, к которому относится это действие
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_правила")
    private Rule правило;

    // устройство, к которому применяется действие
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_цели")
    private Device устройство;

    // тип действия
    @Enumerated(EnumType.STRING)
    @Column(name = "тип_действия")
    private ActionType типДействия;

    // значение, которое будет установлено устройству
    @Column(name = "значение")
    private String значение;
} 