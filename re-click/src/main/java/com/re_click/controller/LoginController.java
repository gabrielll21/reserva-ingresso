package com.re_click.controller;

import com.re_click.model.Usuario;
import com.re_click.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Melhor prática para verificar senhas
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        // Adiciona a URL de cadastro ao modelo (opcional se já estiver fixo no HTML)
        model.addAttribute("urlCadastro", "/cadastro");
        return "login";
    }

    @PostMapping("/login")
    public String processarLogin(@RequestParam String email,
                                 @RequestParam String senha,
                                 Model model) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent() &&
                passwordEncoder.matches(senha, usuarioOptional.get().getSenha())) {
            return "redirect:/home"; // Redireciona para a página inicial
        }

        model.addAttribute("erro", "Credenciais inválidas"); // Mensagem mais segura
        return "login";
    }
}
