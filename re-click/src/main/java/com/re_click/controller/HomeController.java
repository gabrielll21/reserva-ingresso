package com.re_click.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String mostrarIndex() {
        return "index"; // Deve haver um templates/index.html
    }

    @GetMapping("/home")
    public String mostrarHome() {
        return "home"; // Se tiver uma tela separada após login
    }
}
