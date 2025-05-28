package com.smarthome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// контроллер для главной страницы
@Controller
public class HomeController {
    // перенаправление на дашборд
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
} 