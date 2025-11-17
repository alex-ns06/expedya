package br.pucpr.expedya.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "passagens")
public class Passagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passagens_generator")
    @SequenceGenerator(name = "passagens_generator", sequenceName = "passagens_id_seq", allocationSize = 1)
    @Column(name = "id")
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

    // Mantemos referência explícita à companhia aérea (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_companhiaaerea_id")
    @ToString.Exclude
    private CompanhiaAerea companhiaAerea;

    // Avião usado pela passagem
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_aviao_id")
    @ToString.Exclude
    private Aviao aviao;

    // Clientes que possuem essa passagem (1 passagem pode ter vários clientes)
    @OneToMany(mappedBy = "passagem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Cliente> clientes = new ArrayList<>();
}
