package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.AviaoDTO;
import br.pucpr.expedya.service.AviaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/avioes")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')") // Protegendo todo o controller para ADMIN
public class AviaoController {

    private final AviaoService aviaoService;

    public AviaoController(AviaoService aviaoService) {
        this.aviaoService = aviaoService;
    }

    @PostMapping
    public ResponseEntity<AviaoDTO> save(@Valid @RequestBody AviaoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aviaoService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<AviaoDTO>> findAll() {
        return ResponseEntity.ok(aviaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AviaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(aviaoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AviaoDTO> update(@PathVariable Long id, @Valid @RequestBody AviaoDTO dto) {
        return ResponseEntity.ok(aviaoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aviaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}