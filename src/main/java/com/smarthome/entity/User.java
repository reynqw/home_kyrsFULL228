package com.smarthome.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Set;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
// класс пользователя системы
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition = "INT")
    // id пользователя
    private Integer userId;

    @Column(nullable = false, unique = true)
    // имя пользователя (логин)
    private String username;

    @Column(name = "password_hash", nullable = false)
    // хэш пароля
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    // роль пользователя (USER или ADMIN)
    private UserRole role = UserRole.USER;

    @Column(unique = true)
    // email пользователя
    private String email;

    @OneToMany(mappedBy = "пользователь", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    // устройства, привязанные к пользователю
    private Set<Device> devices;

    @OneToMany(mappedBy = "пользователь", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ActionLog> logs;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserDevice> userDevices;
} 