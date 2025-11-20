package br.pucpr.expedya.controller;

import br.pucpr.expedya.config.GlobalApiResponses;
import br.pucpr.expedya.dto.CompanhiaAereaDTO;
import br.pucpr.expedya.service.CompanhiaAereaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companhias")
@Tag(name = "companhia-aerea-controller", description = "Gerencia o cadastro e manutenção de companhias aéreas.")
@GlobalApiResponses
@RequiredArgsConstructor
public class CompanhiaAereaController {

    private final CompanhiaAereaService companhiaAereaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastra companhia aérea", description = "Cria uma nova companhia aérea no sistema.")
    public ResponseEntity<CompanhiaAereaDTO> save(@RequestBody CompanhiaAereaDTO dto) {
        return ResponseEntity.ok(companhiaAereaService.save(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista companhias aéreas", description = "Retorna todas as companhias cadastradas.")
    public ResponseEntity<List<CompanhiaAereaDTO>> findAll() {
        return ResponseEntity.ok(companhiaAereaService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Busca companhia por ID", description = "Retorna os dados de uma companhia aérea específica.")
    public ResponseEntity<CompanhiaAereaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(companhiaAereaService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza companhia aérea", description = "Atualiza totalmente os dados de uma companhia aérea.")
    public ResponseEntity<CompanhiaAereaDTO> update(@PathVariable Long id, @RequestBody CompanhiaAereaDTO dto) {
        return ResponseEntity.ok(companhiaAereaService.update(id, dto));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualização parcial de companhia aérea", description = "Atualiza apenas campos específicos da companhia aérea.")
    public ResponseEntity<CompanhiaAereaDTO> partialUpdate(@PathVariable Long id, @RequestBody CompanhiaAereaDTO dto) {
        return ResponseEntity.ok(companhiaAereaService.partialUpdate(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove companhia aérea", description = "Exclui permanentemente uma companhia aérea.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companhiaAereaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
