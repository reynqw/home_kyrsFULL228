package com.smarthome.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "правила")
public class Rule {
    // id правила
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_правила")
    private Integer id;

    // имя правила
    @Column(name = "название", nullable = false)
    private String name;

    @Column(name = "активно")
    private boolean активно = true;

    @Column(name = "дата_создания")
    private LocalDateTime датаСоздания = LocalDateTime.now();

    @OneToMany(mappedBy = "правило", cascade = CascadeType.ALL)
    private List<Condition> условия;

    @OneToMany(mappedBy = "правило", cascade = CascadeType.ALL)
    private List<Action> действия;
} 