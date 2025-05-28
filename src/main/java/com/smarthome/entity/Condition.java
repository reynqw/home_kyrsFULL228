package com.smarthome.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// класс для хранения условий, используемых в правилах автоматизации
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "условия")
public class Condition {
    // id условия
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_условия")
    private Integer id;

    // правило, к которому относится это условие
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_правила")
    private Rule правило;

    @ManyToOne
    @JoinColumn(name = "id_устройства", nullable = false)
    private Device устройство;

    @Column(name = "оператор", nullable = false)
    @Convert(converter = OperatorConverter.class)
    private Operator оператор;

    @Column(name = "значение", nullable = false)
    private String значение;

    @Enumerated(EnumType.STRING)
    @Column(name = "логика")
    private Logic логика = Logic.AND;
}

enum Logic {
    AND,
    OR
} 