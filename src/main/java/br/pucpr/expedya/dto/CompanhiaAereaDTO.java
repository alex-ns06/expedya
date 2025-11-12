package br.pucpr.expedya.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanhiaAereaDTO {
    private Integer id;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @NotBlank
    private String cnpj;

    @NotBlank
    private Integer quantidade_avioes;
}
