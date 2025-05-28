package com.smarthome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// контроллер для регистрации пользователей
@Controller
public class SignupController {
    // отображение страницы регистрации
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
} 