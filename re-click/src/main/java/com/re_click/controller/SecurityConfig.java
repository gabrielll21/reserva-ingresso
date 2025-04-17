package com.re_click.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/", "/login").permitAll() // libera acesso aos recursos estáticos e à página inicial
                        .anyRequest().authenticated() // outras páginas precisam de login
                )
                .formLogin(form -> form
                        .loginPage("/") // a sua página de login customizada
                        .loginProcessingUrl("/login") // endpoint para processar o formulário
                        .defaultSuccessUrl("/home", true) // para onde o usuário vai depois de logar
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // para onde redirecionar após logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

