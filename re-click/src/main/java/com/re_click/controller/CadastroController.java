package com.re_click.controller;

import com.re_click.model.Usuario;
import com.re_click.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastroController {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CadastroController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/cadastro")
    public String mostrarCadastro(Model model) {
        model.addAttribute("erro", null);
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@RequestParam String nome,
                                    @RequestParam String email,
                                    @RequestParam String senha,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        System.out.println("Tentando cadastrar: " + email);

        if (usuarioRepository.findByEmail(email).isPresent()) {
            System.out.println("Erro: email já existente");
            model.addAttribute("erro", "Este e-mail já está em uso.");
            return "cadastro";
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(passwordEncoder.encode(senha));
        usuarioRepository.save(novoUsuario);
        System.out.println("Usuário cadastrado com sucesso: " + email);

        // adiciona mensagem flash e redireciona para /login
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Cadastro realizado com sucesso! Agora faça login.");
        return "redirect:/login";
    }
}
