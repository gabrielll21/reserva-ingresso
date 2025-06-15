
    package com.re_click.config; // ou com.re_click.handler

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

    @Component
    public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {

            // URL padrão para redirecionamento
            String redirectUrl = "/";

            // Pega as "roles" (autoridades) do usuário que fez o login
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                // Se a role for ROLE_ADMIN, muda a URL de redirecionamento
                if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                    redirectUrl = "/admin";
                    break; // Encontrou a role de admin, não precisa procurar mais
                }
                // Você poderia adicionar outras lógicas aqui, por exemplo, para vendedores
                // if (grantedAuthority.getAuthority().equals("ROLE_VENDEDOR")) {
                //     redirectUrl = "/perfilvendedor";
                //     break;
                // }
            }

            // Redireciona o usuário para a URL definida
            response.sendRedirect(redirectUrl);
        }
    }

