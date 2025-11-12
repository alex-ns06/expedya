package br.pucpr.expedya.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "companhiaaerea")
public class CompanhiaAerea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "avioes")
    private Integer quantidade_avioes;
}
