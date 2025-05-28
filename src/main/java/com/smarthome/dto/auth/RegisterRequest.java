package com.smarthome.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Логин должен содержать только латинские буквы и цифры.") // логин: только латинские буквы и цифры
    private String username;

    @NotBlank
    @Size(min = 8, message = "Пароль должен быть не менее 8 символов.")
    @Pattern(regexp = ".*[a-z].*", message = "Пароль должен содержать хотя бы одну строчную букву.") // хотя бы одна строчная
    @Pattern(regexp = ".*[A-Z].*", message = "Пароль должен содержать хотя бы одну заглавную букву.") // хотя бы одна заглавная
    @Pattern(regexp = ".*\\d.*", message = "Пароль должен содержать хотя бы одну цифру.") // хотя бы одна цифра
    private String password;

    @NotBlank
    @Email(message = "Введите корректный e-mail.")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(USER|ADMIN)$", message = "Роль должна быть USER или ADMIN.")
    private String role;
} 