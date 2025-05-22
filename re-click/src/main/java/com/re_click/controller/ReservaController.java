package com.re_click.controller;

import com.re_click.model.Evento;
import com.re_click.model.Reserva;
import com.re_click.model.Usuario;
import com.re_click.repository.EventoRepository;
import com.re_click.repository.ReservaRepository;
import com.re_click.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaRepository reservaRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaController(ReservaRepository reservaRepository,
                             EventoRepository eventoRepository,
                             UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/criar/{idEvento}")
    public String reservarIngresso(@PathVariable Long idEvento, Authentication auth) {
        Usuario usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
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
}
