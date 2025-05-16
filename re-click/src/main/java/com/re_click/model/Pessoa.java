package com.re_click.model;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nome;
    protected String email;
    protected String senha;
    protected String cpf;
}
