package br.pucpr.expedya.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "avioes")
public class Aviao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String modelo;

    @Column(name = "codigo_aeronave", nullable = false, unique = true)
    private String codigoAeronave;

    @Column(name = "capacidade_passageiros", nullable = false)
    private Integer capacidadePassageiros;

    /**
     * Muitos aviões pertencem a uma companhia aérea.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_companhiaaerea_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private CompanhiaAerea companhiaAerea;

    /**
     * Passagens associadas a este avião.
     */
    @OneToMany(mappedBy = "aviao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<Passagem> passagens = new HashSet<>();

}
