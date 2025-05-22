package com.re_click.controller;

import com.re_click.model.Evento;
import com.re_click.model.Reserva;
import com.re_click.model.Usuario;
import com.re_click.model.Vendedor;
import com.re_click.repository.EventoRepository;
import com.re_click.repository.ReservaRepository;
import com.re_click.repository.UsuarioRepository;
import com.re_click.repository.VendedorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    private final UsuarioRepository usuarioRepository;
    private final VendedorRepository vendedorRepository;
    private final EventoRepository eventoRepository;
    private final ReservaRepository reservaRepository; // 🔴 ADICIONAR ISSO

    public ProfileController(UsuarioRepository usuarioRepository,
                             VendedorRepository vendedorRepository,
                             EventoRepository eventoRepository,
                             ReservaRepository reservaRepository) { // 🔴 ADICIONAR ISSO
        this.usuarioRepository = usuarioRepository;
        this.vendedorRepository = vendedorRepository;
        this.eventoRepository = eventoRepository;
        this.reservaRepository = reservaRepository; // 🔴 ADICIONAR ISSO
    }

    @GetMapping("/perfilusuario")
    public String perfilUsuario(Authentication auth, Model model) {
        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // ✅ Pegar eventos reservados pelo usuário
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

        model.addAttribute("vendedor", vendedor);
        model.addAttribute("eventos", eventos);
        return "perfilvendedor";
    }
}

