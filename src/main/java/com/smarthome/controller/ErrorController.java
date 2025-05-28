package com.smarthome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// контроллер для обработки ошибок
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    // обработка ошибки и перенаправление на страницу входа
    @GetMapping("/error")
    public String handleError() {
        return "redirect:/login";
    }
} 