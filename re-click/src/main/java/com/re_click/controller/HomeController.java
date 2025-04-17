package com.re_click.controller;

import com.re_click.model.Evento;
import com.re_click.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "login";
    }


    @GetMapping("/cadastrarEvento")
    public String mostrarFormulario(Model model) {
        model.addAttribute("evento", new Evento());
        return "cadastrarEvento";
    }
}

