package br.pucpr.expedya.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.*;

@Data
@Entity
@Table(name = "passagens")
public class Passagem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "origem")
    private String origem;

    @Column(name = "destino")
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
    @JoinColumn(name = "fk_companhiaaerea_id")
    private CompanhiaAerea companhiaAerea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_aviao_id")
    private Aviao aviao;
}
