package br.pucpr.expedya.dto;

import br.pucpr.expedya.security.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;

    @NotNull
    @NotBlank
    private String nomeCompleto;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String telefone;

    @NotNull
    @NotBlank
    private String cpf;

    @NotNull
    @NotBlank
    private String passaporte;

    @NotNull
    @NotBlank
    private String senha;

    private Role role;

    private Long passagemId;
}
