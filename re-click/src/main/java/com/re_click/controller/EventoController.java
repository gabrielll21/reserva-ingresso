package com.re_click.controller;

import com.re_click.model.Evento;
import com.re_click.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/eventos") // Define uma base para as rotas de eventos
public class EventoController {

    private final EventoService eventoService;

    // Injeção de dependência via construtor
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // Exibir página com todos os eventos
    @GetMapping
    public String mostrarEventos(Model model) {
        List<Evento> listaEventos = eventoService.listarEventos();
        model.addAttribute("eventos", listaEventos);
        return "eventos"; // Retorna o template eventos.html
    }

    // Exibir o formulário para cadastro de evento
    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("evento", new Evento()); // Cria um novo objeto Evento para o formulário
        return "eventoFormulario"; // Retorna o template eventoFormulario.html
    }

    // Salvar novo evento
    @PostMapping("/cadastrar")
    public String salvarEvento(@ModelAttribute Evento evento, Model model) {
        try {
            eventoService.salvarEvento(evento);
            return "redirect:/eventos"; // Redireciona para a lista de eventos
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cadastrar evento: " + e.getMessage());
            return "eventoFormulario"; // Retorna para o formulário de cadastro
        } finally {

        }
    }
}
