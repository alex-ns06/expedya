package br.pucpr.expedya.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefone;

    @Column(nullable = false, unique = true)
    private String cpf;

    private String passaporte;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String role; // ADMIN / USER
}