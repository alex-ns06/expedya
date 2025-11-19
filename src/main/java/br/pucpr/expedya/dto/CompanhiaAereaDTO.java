package br.pucpr.expedya.dto;

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
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @CNPJ(message = "CNPJ inválido ou já cadastrado")
    private String cnpj;

    private Set<Long> avioesId;
    private Set<Long> passagensId;

    private Set<String> modelosAvioes;
}