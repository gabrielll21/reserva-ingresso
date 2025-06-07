package com.re_click.repository;

import com.re_click.model.Evento;
import com.re_click.model.StatusEvento;
import com.re_click.model.Usuario;
import com.re_click.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByVendedor(Vendedor vendedor);
    List<Evento> findByStatus(StatusEvento status);
    long countByStatus(StatusEvento status);

}