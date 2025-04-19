package com.re_click.controller;

import com.re_click.model.Usuario;
import com.re_click.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CadastroController {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CadastroController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/cadastro")
    public String mostrarCadastro() {
        return "cadastro"; // Espera um arquivo templates/cadastro.html
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@RequestParam String nome,
                                    @RequestParam String email,
                                    @RequestParam String senha,
                                    Model model) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("erro", "Email já está em uso!");
            return "cadastro";

        }



        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(passwordEncoder.encode(senha));

        usuarioRepository.save(novoUsuario);

        return "redirect:/login";
    }
}
