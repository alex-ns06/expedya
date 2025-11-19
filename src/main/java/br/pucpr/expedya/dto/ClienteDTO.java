package br.pucpr.expedya.dto;

import br.pucpr.expedya.security.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set; // Importante para a lista de IDs

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;

    @NotBlank
    private String nomeCompleto;

    @NotBlank
    @Email(message = "E-mail inválido ou já cadastrado")
    private String email;

    @NotBlank
    private String telefone;

    @NotBlank
    @CPF(message = "CPF inválido ou já cadastrado")
    private String cpf;

    @NotBlank
    private String passaporte;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    private Role role;

    private Set<Long> passagensId;
}