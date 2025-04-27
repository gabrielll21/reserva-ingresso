package com.re_click.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Rota para a página inicial
    @GetMapping("/")
    public String mostrarIndex() {
        return "index"; // Retorna o template index.html
    }

    // Rota para o perfil do usuário
    @GetMapping("/perfilusuario")
    public String mostrarPerfilUsuario() {
        return "perfilusuario"; // Retorna o template perfilusuario.html
    }


}

