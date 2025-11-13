package br.pucpr.expedya.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "companhiasaereas")
public class CompanhiaAerea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "fk_passagens_ID")
    private Long passagensId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Pertence",
            joinColumns = @JoinColumn(name = "fk_companhiaaerea_ID"),
            inverseJoinColumns = @JoinColumn(name = "fk_aviao_ID")
    )
    private Set<Aviao> avioes = new HashSet<>();

    @OneToMany(mappedBy = "companhiaAerea")
    private Set<Passagem> passagens = new HashSet<>();
}
