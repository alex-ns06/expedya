package br.pucpr.expedya.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avioes_generator")
    @SequenceGenerator(name = "avioes_generator", sequenceName = "avioes_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "codigo_aeronave", nullable = false, unique = true)
    private String codigoAeronave;

    @Column(name = "capacidade_passageiros", nullable = false)
    private Integer capacidadePassageiros;

    /**
     * Relação de Aviao -> CompanhiaAerea: muitos aviões podem pertencer a uma companhia.
     * Se quiser modelar como outro tipo (many-to-many), podemos ajustar depois.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_companhiaaerea_id")
    @ToString.Exclude
    @JsonIgnore
    private CompanhiaAerea companhiaAerea;

    /**
     * Passagens associadas a este avião.
     */
    @OneToMany(mappedBy = "aviao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<Passagem> passagens = new HashSet<>();
}
