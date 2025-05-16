package com.re_click.repository;

import com.re_click.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    boolean existsByEmail(String email);

    Optional<Vendedor> findByEmail(String email);

}
