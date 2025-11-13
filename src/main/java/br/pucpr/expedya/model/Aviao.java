package br.pucpr.expedya.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "avioes")
public class Aviao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "codigo_aeronave", nullable = false, unique = true)
    private String codigoAeronave;

    @Column(name = "capacidade_passageiros", nullable = false)
    private Integer capacidadePassageiros;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companhia_aerea_id", nullable = false)
    private CompanhiaAerea companhiaAerea;
}