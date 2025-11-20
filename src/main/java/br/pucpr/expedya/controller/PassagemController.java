package br.pucpr.expedya.controller;

import br.pucpr.expedya.config.GlobalApiResponses;
import br.pucpr.expedya.dto.PassagemDTO;
import br.pucpr.expedya.service.PassagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passagens")
@Tag(name = "passagem-controller", description = "Gerencia reserva, consulta e atualização de passagens aéreas.")
@GlobalApiResponses
@RequiredArgsConstructor
public class PassagemController {

    private final PassagemService passagemService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastra passagem", description = "Cria um novo registro de passagem aérea.")
    public ResponseEntity<PassagemDTO> save(@RequestBody PassagemDTO dto) {
        return ResponseEntity.ok(passagemService.save(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista passagens", description = "Retorna todas as passagens cadastradas.")
    public ResponseEntity<List<PassagemDTO>> findAll() {
        return ResponseEntity.ok(passagemService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Busca passagem por ID", description = "Retorna os dados de uma passagem específica.")
    public ResponseEntity<PassagemDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(passagemService.findById(Long.valueOf(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza passagem", description = "Atualiza totalmente os dados de uma passagem aérea.")
    public ResponseEntity<PassagemDTO> update(@PathVariable Integer id, @RequestBody PassagemDTO dto) {
        return ResponseEntity.ok(passagemService.update(Long.valueOf(id), dto));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualização parcial de passagem", description = "Altera parcialmente os dados de uma passagem.")
    public ResponseEntity<PassagemDTO> partialUpdate(@PathVariable Integer id, @RequestBody PassagemDTO dto) {
        return ResponseEntity.ok(passagemService.partialUpdate(Long.valueOf(id), dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove passagem", description = "Exclui uma passagem aérea pelo ID.")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        passagemService.delete(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}
