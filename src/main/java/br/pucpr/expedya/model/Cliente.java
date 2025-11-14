package br.pucpr.expedya.model;

import br.pucpr.expedya.security.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientes_generator")
    @SequenceGenerator(name = "clientes_generator", sequenceName = "clientes_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "nome")
    private String nomeCompleto;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "passaporte")
    private String passaporte;

    @Column(name = "senha")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "funcao")
    private Role role; // ADMIN / USER

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_passagens_id") // Esta será a FK na tabela 'clientes'
    @JsonBackReference // Evita loop infinito
    private Passagem passagem;
}