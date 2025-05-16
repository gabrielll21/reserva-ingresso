package com.re_click.service;

import com.re_click.model.Vendedor;
import com.re_click.repository.VendedorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VendedorDetailsService implements UserDetailsService {

    private final VendedorRepository vendedorRepository;

    public VendedorDetailsService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return vendedorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Vendedor não encontrado com o email: " + email));
    }
}
