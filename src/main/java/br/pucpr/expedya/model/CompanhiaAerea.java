package br.pucpr.expedya.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "companhias_aereas")
public class CompanhiaAerea {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companhias_generator")
    @SequenceGenerator(name = "companhias_generator", sequenceName = "companhias_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cnpj")
    private String cnpj;

    /**
     * Aviões dessa companhia
     */
    @OneToMany(mappedBy = "companhiaAerea", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Aviao> avioes = new HashSet<>();

    /**
     * Passagens operadas por essa companhia (opcional: pode ser derivada via avioes -> passagens)
     */
    @OneToMany(mappedBy = "companhiaAerea", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Passagem> passagens = new HashSet<>();
}
