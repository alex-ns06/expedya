package br.pucpr.expedya.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "passagens")
public class Passagem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
}
