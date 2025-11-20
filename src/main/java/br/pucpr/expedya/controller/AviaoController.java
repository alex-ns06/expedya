package br.pucpr.expedya.controller;

import br.pucpr.expedya.config.GlobalApiResponses;
import br.pucpr.expedya.dto.AviaoDTO;
import br.pucpr.expedya.model.Aviao;
import br.pucpr.expedya.service.AviaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avioes")
@GlobalApiResponses
@RequiredArgsConstructor
public class AviaoController {

    private final AviaoService aviaoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AviaoDTO> save(@RequestBody AviaoDTO aviaoDTO) {
        return ResponseEntity.ok(aviaoService.save(aviaoDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AviaoDTO>> findAll() {
        return ResponseEntity.ok(aviaoService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AviaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(aviaoService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AviaoDTO> update(@PathVariable Long id, @RequestBody AviaoDTO dto) {
        return ResponseEntity.ok(aviaoService.update(id, dto));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AviaoDTO> partialUpdate(
            @PathVariable Long id,
            @RequestBody AviaoDTO dto
    ) {
        return ResponseEntity.ok(aviaoService.partialUpdate(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aviaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
