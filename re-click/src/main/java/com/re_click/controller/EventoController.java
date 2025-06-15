package com.re_click.controller;

// Adicione este import se ainda não existir
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.re_click.model.Evento;
import com.re_click.model.StatusEvento;
import com.re_click.model.Vendedor;
import com.re_click.repository.EventoRepository;
import com.re_click.repository.VendedorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    private final EventoRepository eventoRepo;
    private final VendedorRepository vendedorRepo;

    public EventoController(EventoRepository eventoRepo,
                            VendedorRepository vendedorRepo) {
        this.eventoRepo = eventoRepo;
        this.vendedorRepo = vendedorRepo;
    }

    // ... (listarEventos, exibirFormularioCadastro, salvarEvento, detalhesEvento, etc.) ...

    @GetMapping("")
    public String listarEventos(Model model) {
        List<Evento> aprovados = eventoRepo.findByStatus(StatusEvento.APROVADO);
        model.addAttribute("eventos", aprovados);
        return "eventos";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("evento", new Evento());
        return "cadastrarevento";
    }

    @PostMapping("/cadastrar")
    public String salvarEvento(@ModelAttribute Evento evento,
                               Authentication auth) {
        String email = auth.getName();
        Vendedor vend = vendedorRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));

        evento.setVendedor(vend);
        evento.setStatus(StatusEvento.PENDENTE);
        eventoRepo.save(evento);
        return "redirect:/perfilvendedor";
    }

    @GetMapping("/{id}")
    public String detalhesEvento(@PathVariable Long id, Model model) {
        Evento evento = eventoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado: " + id));
        model.addAttribute("evento", evento);
        return "eventoDetalhes";
    }

    @GetMapping("/editar-evento/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model, Authentication auth) {
        Evento evento = eventoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado: " + id));
        Vendedor vendedorLogado = vendedorRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));

        if (!evento.getVendedor().getId().equals(vendedorLogado.getId())) {
            throw new SecurityException("Acesso negado: você não é o proprietário deste evento.");
        }

        model.addAttribute("evento", evento);
        return "editar-evento";
    }

    @PostMapping("/editar/{id}")
    public String salvarEdicao(@PathVariable Long id,
                               @ModelAttribute("evento") Evento eventoEditado,
                               Authentication auth) {

        Evento eventoOriginal = eventoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento inválido ID:" + id));
        Vendedor vendedorLogado = vendedorRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));

        if (!eventoOriginal.getVendedor().getId().equals(vendedorLogado.getId())) {
            throw new SecurityException("Acesso negado: você não pode editar este evento.");
        }

        eventoEditado.setVendedor(eventoOriginal.getVendedor());
        eventoEditado.setStatus(eventoOriginal.getStatus());
        eventoRepo.save(eventoEditado);

        return "redirect:/perfilvendedor";
    }

    // --- MÉTODO DE EXCLUSÃO ADICIONADO ---
    @PostMapping("/excluir/{id}")
    public String excluirEvento(@PathVariable Long id, Authentication auth, RedirectAttributes redirectAttributes) {
        // 1. Carrega o evento e o vendedor logado
        Evento evento = eventoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado: " + id));
        Vendedor vendedorLogado = vendedorRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));

        // 2. Segurança: Verifica se o vendedor logado é o dono do evento
        if (!evento.getVendedor().getId().equals(vendedorLogado.getId())) {
            throw new SecurityException("Acesso negado: você não pode excluir este evento.");
        }

        // 3. Exclui o evento. A cascata configurada na entidade cuidará das reservas.
        eventoRepo.delete(evento);

        // 4. Adiciona uma mensagem de sucesso para a próxima página
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Evento excluído com sucesso!");

        // 5. Redireciona de volta para o perfil do vendedor
        return "redirect:/perfilvendedor";
    }
}