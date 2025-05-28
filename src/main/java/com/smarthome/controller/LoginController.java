package com.smarthome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// контроллер для отображения страницы входа
@Controller
public class LoginController {
    // отображение страницы входа
    @GetMapping("/login")
    public String login() {
        return "login";
    }
} 