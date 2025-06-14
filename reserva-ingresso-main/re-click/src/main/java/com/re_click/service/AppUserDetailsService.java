package com.re_click.service;

import com.re_click.repository.AdminRepository;
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
    private final AdminRepository adminRepository;

    public AppUserDetailsService(UsuarioRepository usuarioRepository,
                                 VendedorRepository vendedorRepository,
                                 AdminRepository adminRepository) {
        this.usuarioRepository = usuarioRepository;
        this.vendedorRepository = vendedorRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).map(u -> (UserDetails) u)
                .or(() -> vendedorRepository.findByEmail(email).map(v -> (UserDetails) v))
                .or(() -> adminRepository.findByEmail(email).map(a -> (UserDetails) a))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário, vendedor ou admin não encontrado: " + email));
    }
}
