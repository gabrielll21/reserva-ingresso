package com.re_click.controller;

import com.re_click.model.Evento;
import com.re_click.model.StatusEvento;
import com.re_click.repository.EventoRepository;
import com.re_click.repository.VendedorRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EventoRepository eventoRepository;
    private final VendedorRepository vendedorRepository;

    public AdminController(EventoRepository eventoRepository, VendedorRepository vendedorRepository) {
        this.eventoRepository = eventoRepository;
        this.vendedorRepository = vendedorRepository;
    }

    @GetMapping
    public String painelAdmin(Model model) {
        long eventosAprovados = eventoRepository.countByStatus(StatusEvento.APROVADO);
        long eventosPendentes = eventoRepository.countByStatus(StatusEvento.PENDENTE);
        long totalVendedores = vendedorRepository.count();

        List<Evento> eventosPendentesList = eventoRepository.findByStatus(StatusEvento.PENDENTE);

        model.addAttribute("eventosAprovados", eventosAprovados);
        model.addAttribute("eventosPendentes", eventosPendentes);
        model.addAttribute("totalVendedores", totalVendedores);
        model.addAttribute("eventosPendentesList", eventosPendentesList);

        return "admin"; // nome da página HTML
    }

    @GetMapping("/aprovar-evento/{id}")
    public String aprovarEvento(@PathVariable Long id) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
        evento.setStatus(StatusEvento.APROVADO);
        eventoRepository.save(evento);
        return "redirect:/admin";
    }

    @GetMapping("/rejeitar-evento/{id}")
    public String rejeitarEvento(@PathVariable Long id) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
        evento.setStatus(StatusEvento.RECUSADO);
        eventoRepository.save(evento);
        return "redirect:/admin";
    }
}
