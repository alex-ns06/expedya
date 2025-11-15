package br.pucpr.expedya.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "aviao")
public class Aviao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "avioes_generator")
    @SequenceGenerator(name = "avioes_generator", sequenceName = "avioes_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "codigo_aeronave", nullable = false, unique = true)
    private String codigoAeronave;

    @Column(name = "capacidade_passageiros", nullable = false)
    private Integer capacidadePassageiros;

    @ManyToMany(mappedBy = "avioes")
    @JsonIgnore
    private Set<CompanhiaAerea> companhias = new HashSet<>();

    @OneToMany(mappedBy = "aviao")
    @JsonIgnore
    private Set<Passagem> passagem = new HashSet<>();
}