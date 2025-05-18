package com.re_click.controller;

import com.re_click.model.Evento;
import com.re_click.model.Vendedor;
import com.re_click.repository.EventoRepository;
import com.re_click.repository.VendedorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String mostrarEventos(Model model) {
        model.addAttribute("eventos", eventoRepo.findAll());
        return "eventos";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("evento", new Evento());
        return "eventoFormulario";
    }

    @PostMapping("/cadastrar")
    public String salvarEvento(@ModelAttribute Evento evento,
                               Authentication auth,
                               Model model) {
        // pega vendedor logado
        String email = auth.getName();
        Vendedor vend = vendedorRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));
        evento.setVendedor(vend);

        eventoRepo.save(evento);
        return "redirect:/perfilvendedor";
    }
}

