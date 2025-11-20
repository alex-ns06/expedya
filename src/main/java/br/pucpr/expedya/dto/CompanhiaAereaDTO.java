package br.pucpr.expedya.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanhiaAereaDTO {

    @Schema(hidden = true)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @CNPJ(message = "CNPJ inválido ou já cadastrado")
    private String cnpj;

    @Schema(hidden = true)
    private Set<Long> avioesId;

    @Schema(hidden = true)
    private Set<Long> passagensId;

    @Schema(hidden = true)
    private Set<String> modelosAvioes;
}