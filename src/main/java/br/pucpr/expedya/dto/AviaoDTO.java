package br.pucpr.expedya.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(hidden = true)
    private Long id;

    @NotBlank
    private String modelo;

    @NotBlank
    private String codigoAeronave;

    @NotNull
    private Integer capacidadePassageiros;

    @NotNull
    private Long companhiaAereaId;

    @Schema(hidden = true)
    private Set<Long> passagensId;
}