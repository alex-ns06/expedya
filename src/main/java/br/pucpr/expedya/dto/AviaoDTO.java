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
    private Long companhiaAereaId;

    private Set<Long> passagensId; // várias passagens
}
