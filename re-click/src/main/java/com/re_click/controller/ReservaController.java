package com.re_click.controller;

import com.re_click.model.*;
import com.re_click.repository.EventoRepository;
import com.re_click.repository.ReservaRepository;
import com.re_click.repository.UsuarioRepository;
import com.re_click.repository.VendedorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaRepository reservaRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VendedorRepository vendedorRepository;

    public ReservaController(ReservaRepository reservaRepository,
                             EventoRepository eventoRepository,
                             UsuarioRepository usuarioRepository, VendedorRepository vendedorRepository) {
        this.reservaRepository = reservaRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.vendedorRepository = vendedorRepository;
    }

    @PostMapping("/criar/{idEvento}")
    public String reservarIngresso(@PathVariable Long idEvento, Authentication auth) {
        Object principal = auth.getPrincipal();
        if (!(principal instanceof Usuario usuario)) {
            throw new IllegalArgumentException("Apenas usuários podem reservar ingressos.");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        Reserva reserva = new Reserva();
        reserva.setEvento(evento);
        reserva.setUsuario(usuario);
        reserva.setQuantidade(1);
        reservaRepository.save(reserva);

        return "redirect:/reservas/confirmada/" + reserva.getId();
    }


    @GetMapping("/confirmada/{id}")
    public String mostrarConfirmacao(@PathVariable Long id, Model model) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));
        model.addAttribute("reserva", reserva);
        return "reservaConfirmada";
    }

    @GetMapping("/meus-ingressos")
    public String listarReservasUsuario(Authentication auth, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        model.addAttribute("reservas", reservaRepository.findAll().stream()
                .filter(reserva -> reserva.getUsuario().getId().equals(usuario.getId()))
                .toList());
        return "meusIngressos";
    }

    @PostMapping("/{id}/confirmar")
    public String confirmarPagamento(@PathVariable Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow();
        reserva.setStatus(StatusPagamento.CONFIRMADO);
        reservaRepository.save(reserva);
        return "redirect:/reservas/vendedor/reservas";
    }

    @PostMapping("/{id}/recusar")
    public String recusarPagamento(@PathVariable Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow();
        reserva.setStatus(StatusPagamento.RECUSADO);
        reservaRepository.save(reserva);
        return "redirect:/reservas/vendedor/reservas";
    }

    @GetMapping("/vendedor/reservas")
    public String listarReservasDoVendedor(Authentication auth, Model model) {
        Vendedor vendedor = vendedorRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));

        List<Reserva> reservas = reservaRepository.findAll().stream()
                .filter(r -> r.getEvento().getVendedor().getId().equals(vendedor.getId()))
                .toList();

        model.addAttribute("reservas", reservas);
        return "reservasVendedor";
    }


}
