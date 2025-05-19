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

    // Rota para a página inicial
    @GetMapping("/")
    public String mostrarIndex(Model model) {
        // busca todos os eventos
        List<Evento> todos = eventoService.listarEventos();

        // adiciona no model para Thymeleaf iterar
        model.addAttribute("eventos", todos);
        // pega só os 3 primeiros como “populares”
        model.addAttribute("populares", todos.stream().limit(3).toList());

        return "index";  // index.html deve usar ${eventos} e ${populares}
    }

//    // Rota para o perfil do usuário
//    @GetMapping("/perfilusuario")
//    public String mostrarPerfilUsuario() {
//        return "perfilusuario"; // Retorna o template perfilusuario.html
//    }


}

