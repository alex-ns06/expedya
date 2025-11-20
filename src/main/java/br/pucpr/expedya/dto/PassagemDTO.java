package br.pucpr.expedya.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassagemDTO {

    @Schema(hidden = true)
    private Long id;

    @NotBlank
    private String origem;

    @NotBlank
    private String destino;

    @NotNull
    private LocalDate dataPartida;

    @NotNull
    private LocalTime horaPartida;

    @NotBlank
    private String assento;

    @NotBlank
    private String classe;

    // Avião pode ser null na compra (voo não alocado ainda), então sem @NotNull
    private Long aviaoId;

    // ALTERAÇÃO: Adicionado @NotNull (Obrigatório)
    @NotNull(message = "O ID da companhia aérea é obrigatório")
    private Long companhiaAereaId;

    // ALTERAÇÃO: Adicionado @NotNull (Obrigatório)
    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;
}