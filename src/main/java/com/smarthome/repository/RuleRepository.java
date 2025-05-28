package com.smarthome.repository;

import com.smarthome.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

// репозиторий для работы с сущностью Rule (правила автоматизации)
public interface RuleRepository extends JpaRepository<Rule, Integer> {
    // поиск всех правил пользователя
    //    List<Rule> findByUser_UserId(Integer userId);

    List<Rule> findByАктивно(boolean активно);

    // Removed the custom query due to MultipleBagFetchException

    @Query("SELECT DISTINCT r FROM Rule r LEFT JOIN FETCH r.действия LEFT JOIN FETCH r.условия")
    List<Rule> findAllWithActionsAndConditions();

    @Query("SELECT DISTINCT r FROM Rule r LEFT JOIN FETCH r.действия")
    List<Rule> findAllWithActions();

    @Query("SELECT DISTINCT r FROM Rule r LEFT JOIN FETCH r.условия")
    List<Rule> findAllWithConditions();
} 