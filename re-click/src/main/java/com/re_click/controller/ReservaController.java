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
import java.util.UUID;

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
        // FORMA CORRETA DE OBTER O USUÁRIO LOGADO
        String emailUsuarioLogado = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário logado não encontrado no banco de dados."));

        // O resto do seu código permanece igual e agora deve funcionar
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
        return "reservaConfirmada"; // Supondo que você tenha essa página
    }

    @GetMapping("/meus-ingressos")
    public String listarReservasUsuario(Authentication auth, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // CORRIGIDO: Usa o método do repositório para buscar de forma eficiente
        List<Reserva> reservas = reservaRepository.findByUsuario(usuario);

        model.addAttribute("reservas", reservas);
        return "meusIngressos";
    }

    @PostMapping("/{id}/confirmar")
    public String confirmarPagamento(@PathVariable Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow();

        // Altera o status para CONFIRMADO
        reserva.setStatus(StatusPagamento.CONFIRMADO);

        // Gera e define o código de confirmação
        String codigo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        reserva.setCodigoConfirmacao(codigo);

        // Salva a reserva com o novo status e o código
        reservaRepository.save(reserva);

        // Redireciona de volta para a lista de compradores do evento específico
        return "redirect:/reservas/evento/" + reserva.getEvento().getId();
    }

    @PostMapping("/{id}/recusar")
    public String recusarPagamento(@PathVariable Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow();
        reserva.setStatus(StatusPagamento.RECUSADO);
        // Opcional: limpar o código de confirmação se houver
        reserva.setCodigoConfirmacao(null);
        reservaRepository.save(reserva);

        // Redireciona de volta para a lista de compradores do evento específico
        return "redirect:/reservas/evento/" + reserva.getEvento().getId();
    }

    @GetMapping("/vendedor/reservas")
    public String listarReservasDoVendedor(Authentication auth, Model model) {
        Vendedor vendedor = vendedorRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));

        // CORRIGIDO: Usa o método do repositório para buscar de forma eficiente
        List<Reserva> reservas = reservaRepository.findByEvento_Vendedor(vendedor);

        model.addAttribute("reservas", reservas);
        return "reservasVendedor";
    }

    @GetMapping("/evento/{id}")
    public String listarCompradoresDeEvento(@PathVariable Long id, Authentication auth, Model model) {
        Vendedor vendedor = vendedorRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        if (!evento.getVendedor().getId().equals(vendedor.getId())) {
            throw new SecurityException("Acesso negado");
        }

        // CORRIGIDO: Usa o método do repositório para buscar de forma eficiente
        List<Reserva> reservas = reservaRepository.findByEvento(evento);

        model.addAttribute("evento", evento);
        model.addAttribute("reservas", reservas);
        return "reservasEvento";
    }
}