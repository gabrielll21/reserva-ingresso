package com.re_click.config;

import com.re_click.service.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// Importe a nova classe
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
    private final AppUserDetailsService appUserDetailsService;
    // 1. Declare o novo handler
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    // 2. Injete o handler no construtor
    public SecurityConfig(AppUserDetailsService appUserDetailsService,
                          AuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.appUserDetailsService = appUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // (suas regras de autorização permanecem as mesmas)
                        .requestMatchers(
                                "/", "/login", "/cadastro", "/css/**", "/js/**", "/images/**",
                                "/eventos/**", "/h2-console/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        // 3. Substitua a linha defaultSuccessUrl pelo seu handler
                        // .defaultSuccessUrl("/", true) // <-- REMOVA ESTA LINHA
                        .successHandler(customAuthenticationSuccessHandler) // <-- ADICIONE ESTA LINHA
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }

    // O resto da classe continua igual...
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder encoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(appUserDetailsService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}