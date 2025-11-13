package br.pucpr.expedya.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AviaoDTO {
    private Long id;

    @NotBlank
    private String modelo;

    @NotBlank
    private String codigoAeronave;

    @NotNull
    private Integer capacidadePassageiros;

    @NotNull
    private Set<Long> companhiaAereaId; // ID para integração

    private Set<String> nomesCompanhiasAereas; // Campo opcional para leitura

    @NotNull
    private Set<Long> passagemId; // ID para integração
}