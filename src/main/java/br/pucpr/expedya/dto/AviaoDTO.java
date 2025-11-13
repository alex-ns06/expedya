package br.pucpr.expedya.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long companhiaAereaId; // ID para integração

    private String nomeCompanhiaAerea; // Campo opcional para leitura
}