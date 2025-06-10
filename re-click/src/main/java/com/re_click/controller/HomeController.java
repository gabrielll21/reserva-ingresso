package com.re_click.controller;

import com.re_click.model.Evento;
import com.re_click.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private final EventoService eventoService;

    public HomeController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/")
    public String mostrarIndex(@RequestParam(required = false) String localFiltro, Model model) {
        // 1) busca apenas eventos com status APROVADO
        List<Evento> aprovados = eventoService.listarEventosAprovados();

        // 2) aplica filtro de local, se houver
        if (localFiltro != null && !localFiltro.isBlank()) {
            aprovados = aprovados.stream()
                    .filter(e -> localFiltro.equals(e.getLocal()))
                    .toList();
            model.addAttribute("localSelecionado", localFiltro);
        }

        // 3) popula as listas no model
        model.addAttribute("eventos", aprovados);
        model.addAttribute("populares", aprovados.stream().limit(3).toList());

        // 4) lista de locais distintos para o dropdown
        List<String> locais = eventoService.listarLocalidadesAprovadas();
        model.addAttribute("locais", locais);

        return "index";  // Thymeleaf usará ${eventos}, ${populares} e ${locais}
    }
}

