package com.re_click.repository;

import com.re_click.model.Evento;
import com.re_click.model.StatusEvento;
import com.re_click.model.Usuario;
import com.re_click.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByVendedor(Vendedor vendedor);
    List<Evento> findByStatus(StatusEvento status);
    long countByStatus(StatusEvento status);
    @Query("SELECT DISTINCT e.local FROM Evento e WHERE e.status = :status")
    List<String> findDistinctLocalByStatus(@Param("status") StatusEvento status);
}