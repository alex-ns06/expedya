package br.pucpr.expedya.model;

import br.pucpr.expedya.security.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientes_generator")
    @SequenceGenerator(name = "clientes_generator", sequenceName = "clientes_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nomeCompleto;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "passaporte")
    private String passaporte;

    /**
     * Não será exposto nas respostas JSON (apenas recebido em requests).
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senha")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "funcao")
    private Role role; // ADMIN / USER

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_passagens_id")
    @JsonBackReference
    @ToString.Exclude
    private Passagem passagem;
}
