package com.re_click.controller;

import com.re_click.model.*;
import com.re_click.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController {

    private final UsuarioRepository usuarioRepository;
    private final VendedorRepository vendedorRepository;
    private final EventoRepository eventoRepository;
    private final ReservaRepository reservaRepository;

    public ProfileController(UsuarioRepository usuarioRepository,
                             VendedorRepository vendedorRepository,
                             EventoRepository eventoRepository,
                             ReservaRepository reservaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.vendedorRepository = vendedorRepository;
        this.eventoRepository = eventoRepository;
        this.reservaRepository = reservaRepository;
    }

    @GetMapping("/perfilusuario")
    public String perfilUsuario(Authentication auth, Model model) {
        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        List<Evento> eventos = reservaRepository.findAll().stream()
                .filter(r -> r.getUsuario().getId().equals(usuario.getId()))
                .map(Reserva::getEvento)
                .distinct()
                .toList();

        model.addAttribute("usuario", usuario);
        model.addAttribute("eventos", eventos);
        return "perfilusuario";
    }

    @GetMapping("/perfilvendedor")
    public String perfilVendedor(Authentication auth, Model model) {
        String email = auth.getName();
        Vendedor vendedor = vendedorRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));

        List<Evento> eventos = eventoRepository.findByVendedor(vendedor);

        // Novo mapa com valores formatados como String
        Map<Long, String> totaisArrecadados = new HashMap<>();
        for (Evento evento : eventos) {
            BigDecimal total = reservaRepository.findAll().stream()
                    .filter(r -> r.getEvento().getId().equals(evento.getId()))
                    .filter(r -> r.getStatus() == StatusPagamento.CONFIRMADO)
                    .map(r -> evento.getPreco().multiply(BigDecimal.valueOf(r.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Formatar já como string com R$
            String formatado = "R$ " + String.format("%.2f", total).replace('.', ',');
            totaisArrecadados.put(evento.getId(), formatado);
        }

        model.addAttribute("vendedor", vendedor);
        model.addAttribute("eventos", eventos);
        model.addAttribute("totaisArrecadados", totaisArrecadados); // ✅ já como String formatada

        return "PerfilVendedor";
    }

}