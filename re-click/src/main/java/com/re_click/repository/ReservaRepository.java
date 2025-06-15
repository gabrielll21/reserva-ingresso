

package com.re_click.repository;

import com.re_click.model.Evento;
import com.re_click.model.Reserva;
import com.re_click.model.Usuario;
import com.re_click.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List; // Não se esqueça de importar List

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /**
     * Encontra todas as reservas para um evento específico.
     * O Spring Data JPA cria a consulta automaticamente pelo nome do método.
     */
    List<Reserva> findByEvento(Evento evento);

    /**
     * Encontra todas as reservas para eventos que pertencem a um vendedor específico.
     * O Spring Data JPA navega pelos relacionamentos: Reserva -> Evento -> Vendedor.
     */
    List<Reserva> findByEvento_Vendedor(Vendedor vendedor);

    List<Reserva> findByUsuario(Usuario usuario);
}