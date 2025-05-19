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

    public CadastroController(UsuarioRepository usuarioRepository,
                              VendedorRepository vendedorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.vendedorRepository = vendedorRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/cadastro")
    public String mostrarCadastro(@RequestParam(required = false) String nome,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) TipoConta tipoConta,
                                  @RequestParam(required = false) String telefone,
                                  @RequestParam(required = false, name = "nomeEmpresa") String nomeEmpresa,
                                  @RequestParam(required = false) String erro,
                                  Model model) {
        model.addAttribute("nome", nome);
        model.addAttribute("email", email);
        model.addAttribute("tipoConta", tipoConta == null ? TipoConta.USUARIO : tipoConta);
        model.addAttribute("telefone", telefone);
        model.addAttribute("nomeEmpresa", nomeEmpresa);
        model.addAttribute("erro", erro);
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@RequestParam String nome,
                                    @RequestParam String email,
                                    @RequestParam String senha,
                                    @RequestParam String confirmacaoSenha,
                                    @RequestParam TipoConta tipoConta,
                                    @RequestParam(required = false) String telefone,
                                    @RequestParam(required = false, name = "nomeEmpresa") String nomeEmpresa,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {

        // Regras de validação...
        if (!senha.equals(confirmacaoSenha)) {
            redirectAttributes.addAttribute("erro", "As senhas não coincidem.");
            // repassa TODOS os campos, pra não perder nada
            redirectAttributes
                    .addAttribute("nome", nome)
                    .addAttribute("email", email)
                    .addAttribute("tipoConta", tipoConta)
                    .addAttribute("telefone", telefone)
                    .addAttribute("nomeEmpresa", nomeEmpresa);
            return "redirect:/cadastro";
        }

        if (usuarioRepository.findByEmail(email).isPresent() ||
                vendedorRepository.existsByEmail(email)) {
            redirectAttributes.addAttribute("erro", "Este e-mail já está em uso.");
            redirectAttributes
                    .addAttribute("nome", nome)
                    .addAttribute("email", email)
                    .addAttribute("tipoConta", tipoConta)
                    .addAttribute("telefone", telefone)
                    .addAttribute("nomeEmpresa", nomeEmpresa);
            return "redirect:/cadastro";
        }

        if (tipoConta == TipoConta.VENDEDOR) {
            if (telefone == null || telefone.isBlank() ||
                    nomeEmpresa == null || nomeEmpresa.isBlank()) {
                redirectAttributes.addAttribute("erro",
                        "Telefone e nome da empresa são obrigatórios para vendedores.");
                redirectAttributes
                        .addAttribute("nome", nome)
                        .addAttribute("email", email)
                        .addAttribute("tipoConta", tipoConta);
                return "redirect:/cadastro";
            }
            Vendedor vendedor = new Vendedor();
            vendedor.setNome(nome);
            vendedor.setEmail(email);
            vendedor.setSenha(passwordEncoder.encode(senha));
            vendedor.setTelefone(telefone);
            vendedor.setNomeEmpresa(nomeEmpresa);
            vendedorRepository.save(vendedor);
        } else {
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(passwordEncoder.encode(senha));
            usuarioRepository.save(usuario);
        }

        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Cadastro realizado com sucesso! Agora faça login.");
        return "redirect:/login";
    }
}
