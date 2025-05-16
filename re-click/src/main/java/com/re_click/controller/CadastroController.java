package com.re_click.controller;

import com.re_click.model.TipoConta;
import com.re_click.model.Usuario;
import com.re_click.model.Vendedor;
import com.re_click.repository.UsuarioRepository;
import com.re_click.repository.VendedorRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastroController {

    private final UsuarioRepository usuarioRepository;
    private final VendedorRepository vendedorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CadastroController(UsuarioRepository usuarioRepository, VendedorRepository vendedorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.vendedorRepository = vendedorRepository;
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
                                    @RequestParam String confirmacaoSenha,
                                    @RequestParam TipoConta tipoConta,
                                    @RequestParam(required = false) String telefone,
                                    @RequestParam(required = false) String nome_empresa,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {

        // Verifica se as senhas coincidem
        if (!senha.equals(confirmacaoSenha)) {
            model.addAttribute("erro", "As senhas não coincidem.");
            return "cadastro";
        }

        // Verifica se o e-mail já está em uso
        if (usuarioRepository.findByEmail(email).isPresent() || vendedorRepository.existsByEmail(email)) {
            model.addAttribute("erro", "Este e-mail já está em uso.");
            return "cadastro";
        }

        // Lógica de criação
        if (tipoConta == TipoConta.VENDEDOR) {
            if (telefone == null || telefone.isBlank() || nome_empresa == null || nome_empresa.isBlank()) {
                model.addAttribute("erro", "Telefone e nome da empresa são obrigatórios para vendedores.");
                return "cadastro";
            }

            Vendedor vendedor = new Vendedor();
            vendedor.setNome(nome);
            vendedor.setEmail(email);
            vendedor.setSenha(passwordEncoder.encode(senha));
            vendedor.setTelefone(telefone);
            vendedor.setNome_empresa(nome_empresa);

            vendedorRepository.save(vendedor);
            System.out.println("Vendedor cadastrado: " + email);

        } else {
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(passwordEncoder.encode(senha));

            usuarioRepository.save(usuario);
            System.out.println("Usuário cadastrado: " + email);
        }

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Cadastro realizado com sucesso! Agora faça login.");
        return "redirect:/login";
    }
}
