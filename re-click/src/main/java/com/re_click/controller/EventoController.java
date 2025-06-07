package com.re_click.controller;

import com.re_click.model.Evento;
import com.re_click.model.StatusEvento;
import com.re_click.model.Vendedor;
import com.re_click.repository.EventoRepository;
import com.re_click.repository.VendedorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    private final EventoRepository eventoRepo;
    private final VendedorRepository vendedorRepo;

    public EventoController(EventoRepository eventoRepo,
                            VendedorRepository vendedorRepo) {
        this.eventoRepo = eventoRepo;
        this.vendedorRepo = vendedorRepo;
    }

    // Listar apenas eventos aprovados (público geral)
    @GetMapping("")
    public String listarEventos(Model model) {
        List<Evento> aprovados = eventoRepo.findByStatus(StatusEvento.APROVADO);
        model.addAttribute("eventos", aprovados);
        return "eventos";
    }

    // Exibe formulário de cadastro de evento
    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("evento", new Evento());
        return "cadastrarevento";
    }

    // Salvar novo evento (status PENDENTE)
    @PostMapping("/cadastrar")
    public String salvarEvento(@ModelAttribute Evento evento,
                               Authentication auth) {
        String email = auth.getName();
        Vendedor vend = vendedorRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));

        evento.setVendedor(vend);
        evento.setStatus(StatusEvento.PENDENTE); // novo evento entra como pendente
        eventoRepo.save(evento);
        return "redirect:/perfilvendedor";
    }

    // Detalhes do evento
    @GetMapping("/{id}")
    public String detalhesEvento(@PathVariable Long id, Model model) {
        Evento evento = eventoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado: " + id));

        model.addAttribute("evento", evento);
        return "eventoDetalhes";
    }
}

