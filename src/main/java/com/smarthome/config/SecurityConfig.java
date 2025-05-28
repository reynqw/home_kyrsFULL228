package com.smarthome.config;

import com.smarthome.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // внедряем фильтр для проверки jwt-токена
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // основной метод настройки безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // настраиваем csrf (отключаем для эндпоинтов авторизации)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/auth/**")  // отключаем csrf для эндпоинтов авторизации
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            // настраиваем доступ к разным url
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/signup", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll() // публичные страницы
                .requestMatchers("/api/auth/**").permitAll() // публичные api для авторизации
                .requestMatchers("/logs", "/logs/**").permitAll() // журнал доступен всем
                .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN") // только для админа
                .requestMatchers("/user/**", "/api/user/**").hasAnyRole("USER", "ADMIN") // для пользователя и админа
                .anyRequest().authenticated() // все остальные требуют авторизации
            )
            // настраиваем форму логина
            .formLogin(form -> form
                .loginPage("/login") // страница логина
                .successHandler(customAuthenticationSuccessHandler()) // обработчик успешного входа
                .failureUrl("/login?error=true") // страница при ошибке
                .permitAll()
            )
            // настраиваем логаут
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true") // куда перенаправлять после выхода
                .deleteCookies("JSESSIONID", "XSRF-TOKEN") // удаляем куки
                .invalidateHttpSession(true) // инвалидируем сессию
                .clearAuthentication(true) // очищаем аутентификацию
                .permitAll()
            )
            // настраиваем параметры сессии
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // сессия создаётся только при необходимости
                .maximumSessions(1) // только одна сессия на пользователя
                .expiredUrl("/login?expired=true") // куда перенаправлять при истечении сессии
            )
            // добавляем фильтр для проверки jwt-токена перед стандартной аутентификацией
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // бин для шифрования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // увеличенный фактор сложности
    }

    // бин для менеджера аутентификации
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // обработчик успешного входа: перенаправляет админа в админку, пользователя — на главную
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            var authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                response.sendRedirect("/admin/dashboard");
            } else {
                response.sendRedirect("/dashboard");
            }
        };
    }
} 