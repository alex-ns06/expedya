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
public class CompanhiaAereaDTO {
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cnpj;

    private Set<Long> avioesId;
    private Set<Long> passagensId;

    private Set<String> modelosAvioes;
}

