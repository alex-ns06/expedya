package br.pucpr.expedya.model;

import br.pucpr.expedya.security.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clientes")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientes_generator")
    @SequenceGenerator(name = "clientes_generator", sequenceName = "clientes_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nome")
    private String nomeCompleto;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "passaporte", unique = true)
    private String passaporte;

    @Column(name = "senha")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "funcao")
    private Role role; // ADMIN / USER

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Passagem> passagens = new ArrayList<>();
}