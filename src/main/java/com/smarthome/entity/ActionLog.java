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
@Table(name = "журнал_действий")
public class ActionLog {
    // id записи
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_журнала", columnDefinition = "INT")
    private Integer id;

    // правило, по которому выполнено действие (если есть)
    @ManyToOne
    @JoinColumn(name = "id_правила", nullable = true)
    private Rule правило;

    // ссылка на устройство
    @ManyToOne
    @JoinColumn(name = "id_устройства", nullable = false)
    private Device устройство;

    // ссылка на пользователя
    @ManyToOne
    @JoinColumn(name = "id_пользователя", nullable = false)
    private User пользователь;

    // дата и время действия
    @Column(name = "дата_время")
    private LocalDateTime датаВремя = LocalDateTime.now();

    // описание выполненного действия
    @Column(name = "выполнено")
    private String выполнено;

    // результат действия
    @Column(name = "результат")
    private String результат;

    // значение устройства в момент действия
    @Column(name = "значение_устройства")
    private String значениеУстройства;

    // тип действия (например, SET, DELETE)
    @Column(name = "тип_действия")
    @Enumerated(EnumType.STRING)
    private ActionType типДействия;
} 