package br.pucpr.expedya.controller;

import br.pucpr.expedya.config.GlobalApiResponses;
import br.pucpr.expedya.dto.AviaoDTO;
import br.pucpr.expedya.service.AviaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avioes")
@Tag(name = "Aviões", description = "Gerencia aeronaves utilizadas nos voos.")
@GlobalApiResponses
@RequiredArgsConstructor
public class AviaoController {

    private final AviaoService aviaoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastra um avião", description = "Cria um novo registro de aeronave.")
    public ResponseEntity<AviaoDTO> save(@RequestBody AviaoDTO aviaoDTO) {
        return ResponseEntity.ok(aviaoService.save(aviaoDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista todos os aviões", description = "Retorna todas as aeronaves cadastradas.")
    public ResponseEntity<List<AviaoDTO>> findAll() {
        return ResponseEntity.ok(aviaoService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Busca avião por ID", description = "Retorna os dados de uma aeronave específica.")
    public ResponseEntity<AviaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(aviaoService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza um avião", description = "Atualiza completamente os dados de uma aeronave.")
    public ResponseEntity<AviaoDTO> update(@PathVariable Long id, @RequestBody AviaoDTO dto) {
        return ResponseEntity.ok(aviaoService.update(id, dto));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualização parcial de avião", description = "Atualiza apenas campos específicos da aeronave.")
    public ResponseEntity<AviaoDTO> partialUpdate(
            @PathVariable Long id,
            @RequestBody AviaoDTO dto
    ) {
        return ResponseEntity.ok(aviaoService.partialUpdate(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove um avião", description = "Exclui uma aeronave pelo ID.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aviaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
