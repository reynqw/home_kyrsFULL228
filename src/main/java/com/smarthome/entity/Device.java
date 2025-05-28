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
@Table(name = "устройства")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_устройства", columnDefinition = "INT")
    private Integer id;

    @Column(nullable = false)
    private String название;

    @Column(nullable = false)
    private String тип;

    @Column
    private String расположение;

    @Column
    private String состояние;

    @Column(name = "дата_добавления")
    private LocalDateTime датаДобавления = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_пользователя", nullable = false)
    private User пользователь;

    // Explicit getter for название
    public String getНазвание() {
        return название;
    }

    // Explicit setter for название
    public void setНазвание(String название) {
        this.название = название;
    }
} 