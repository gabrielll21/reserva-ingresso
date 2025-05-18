package com.re_click.service;

import com.re_click.model.Usuario;
import com.re_click.model.Vendedor;
import com.re_click.repository.UsuarioRepository;
import com.re_click.repository.VendedorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final VendedorRepository vendedorRepository;

    public AppUserDetailsService(UsuarioRepository usuarioRepository, VendedorRepository vendedorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.vendedorRepository = vendedorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> (UserDetails) usuario)
                .orElseGet(() ->
                        vendedorRepository.findByEmail(email)
                                .map(vendedor -> (UserDetails) vendedor)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuário ou vendedor não encontrado: " + email))
                );
    }
}
