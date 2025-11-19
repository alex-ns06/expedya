package br.pucpr.expedya.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "passagens")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Passagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passagens_generator")
    @SequenceGenerator(name = "passagens_generator", sequenceName = "passagens_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "origem", nullable = false)
    private String origem;

    @Column(name = "destino", nullable = false)
    private String destino;

    @Column(name = "datapartida")
    private LocalDate dataPartida;

    @Column(name = "horapartida")
    private LocalTime horaPartida;

    @Column(name = "assento")
    private String assento;

    @Column(name = "classe")
    private String classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_companhiaaerea_id", nullable = false)
    @ToString.Exclude
    private CompanhiaAerea companhiaAerea;

    // Avião pode ser opcional no início (ex: venda antes de definir a aeronave exata)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_aviao_id")
    @ToString.Exclude
    private Aviao aviao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cliente_id", nullable = false)
    @ToString.Exclude
    private Cliente cliente;
}