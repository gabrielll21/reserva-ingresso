package com.re_click.config;

import com.re_click.service.UsuarioDetailsService;
import com.re_click.service.VendedorDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;
    private final VendedorDetailsService vendedorDetailsService;

    public SecurityConfig(UsuarioDetailsService usuarioDetailsService, VendedorDetailsService vendedorDetailsService) {
        this.usuarioDetailsService = usuarioDetailsService;
        this.vendedorDetailsService = vendedorDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/", "/login", "/cadastro", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .failureUrl("/login?erro=true")
                        .defaultSuccessUrl("/", true)
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
                        .frameOptions().sameOrigin()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder) throws Exception {
        DaoAuthenticationProvider usuarioProvider = new DaoAuthenticationProvider();
        usuarioProvider.setUserDetailsService(usuarioDetailsService);
        usuarioProvider.setPasswordEncoder(encoder);

        DaoAuthenticationProvider vendedorProvider = new DaoAuthenticationProvider();
        vendedorProvider.setUserDetailsService(vendedorDetailsService);
        vendedorProvider.setPasswordEncoder(encoder);

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(usuarioProvider)
                .authenticationProvider(vendedorProvider)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

