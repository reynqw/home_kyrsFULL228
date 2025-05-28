package com.smarthome.repository;

import com.smarthome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
import java.util.Optional;

// репозиторий для работы с сущностью User
public interface UserRepository extends JpaRepository<User, Integer> {
    // поиск пользователя по имени пользователя (логину)
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    // проверка существования пользователя по имени
    boolean existsByUsername(String username);
    // проверка существования пользователя по email
    boolean existsByEmail(String email);
    @EntityGraph(attributePaths = {"devices", "logs", "userDevices"})
    List<User> findAll();
    @EntityGraph(attributePaths = {"devices", "logs", "userDevices"})
    Optional<User> findByUserId(Integer userId);
} 