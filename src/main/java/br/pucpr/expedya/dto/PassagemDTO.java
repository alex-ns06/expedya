package br.pucpr.expedya.dto;

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

    private Long aviaoId;

    private Long companhiaAereaId;

    private Long clienteId;
}
