package com.re_click.controller;

import com.re_click.model.Evento;
import com.re_click.model.Vendedor;
import com.re_click.repository.EventoRepository;
import com.re_click.repository.VendedorRepository;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("evento", new Evento());
        return "cadastrarevento";
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

    @GetMapping("/{id}")
    public String detalhesEvento(@PathVariable Long id, Model model) {
        Evento evento = eventoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado: " + id));
        model.addAttribute("evento", evento);
        return "eventoDetalhes";
    }

    @PostMapping("/{id}/reservar")
    public String reservarIngresso(@PathVariable Long id, Authentication auth) {
        // Aqui você buscaria o usuário logado, criaria a reserva, etc.
        // Exemplo:
        // Usuario user = usuarioRepo.findByEmail(auth.getName()).get();
        // Reserva r = new Reserva(user, eventoRepo.getOne(id));
        // reservaRepo.save(r);
        return "redirect:/perfilusuario";
    }

    @GetMapping("/teste-detalhe")
    public String testarDetalhe(Model model) {
        Evento e = new Evento();
        e.setNome("Teste");
        e.setData("2025-01-01");
        e.setHorario("20:00");
        e.setLocal("Local Teste");
        e.setDescricao("Evento de teste.");
        e.setChavePix("pix@teste.com");

        model.addAttribute("evento", e);
        return "eventoDetalhes";
    }
}

