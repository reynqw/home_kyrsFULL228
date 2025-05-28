package com.smarthome.controller;

import com.smarthome.entity.Rule;
import com.smarthome.repository.RuleRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
public class RuleRestController {
    private final RuleRepository ruleRepository;

    public RuleRestController(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Rule>> getAllRules() {
        return ResponseEntity.ok(ruleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rule> getRuleById(@PathVariable Integer id) {
        return ruleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Rule> createRule(@Valid @RequestBody Rule rule) {
        return ResponseEntity.ok(ruleRepository.save(rule));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Rule> updateRule(@PathVariable Integer id, @Valid @RequestBody Rule rule) {
        return ruleRepository.findById(id)
                .map(existingRule -> {
                    rule.setId(id);
                    return ResponseEntity.ok(ruleRepository.save(rule));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRule(@PathVariable Integer id) {
        return ruleRepository.findById(id)
                .map(rule -> {
                    ruleRepository.delete(rule);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Rule>> getActiveRules() {
        return ResponseEntity.ok(ruleRepository.findByАктивно(true));
    }
} 