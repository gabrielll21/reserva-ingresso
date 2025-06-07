package com.re_click.controller;

import com.re_click.model.Evento;
import com.re_click.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final EventoService eventoService;

    public HomeController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/")
    public String mostrarIndex(Model model) {
        // pega só os aprovados
        List<Evento> aprovados = eventoService.listarEventosAprovados();
        model.addAttribute("eventos", aprovados);
        model.addAttribute("populares", aprovados.stream().limit(3).toList());
        return "index";  // seu template de home
    }
}

