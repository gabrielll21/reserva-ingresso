package com.re_click.service;

import com.re_click.model.Evento;
import com.re_click.model.StatusEvento;
import com.re_click.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public Evento salvarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public List<Evento> listarEventosAprovados() {
        return eventoRepository.findByStatus(StatusEvento.APROVADO);
    }

    public List<String> listarLocalidadesAprovadas() {
        return eventoRepository.findDistinctLocalByStatus(StatusEvento.APROVADO);
    }

}
