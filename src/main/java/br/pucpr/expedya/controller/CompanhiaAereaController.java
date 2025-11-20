package br.pucpr.expedya.controller;

import br.pucpr.expedya.config.GlobalApiResponses;
import br.pucpr.expedya.dto.CompanhiaAereaDTO;
import br.pucpr.expedya.service.CompanhiaAereaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companhias")
@GlobalApiResponses
@RequiredArgsConstructor
public class CompanhiaAereaController {

    private final CompanhiaAereaService companhiaAereaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanhiaAereaDTO> save(@RequestBody CompanhiaAereaDTO dto) {
        return ResponseEntity.ok(companhiaAereaService.save(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompanhiaAereaDTO>> findAll() {
        return ResponseEntity.ok(companhiaAereaService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanhiaAereaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(companhiaAereaService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanhiaAereaDTO> update(@PathVariable Long id, @RequestBody CompanhiaAereaDTO dto) {
        return ResponseEntity.ok(companhiaAereaService.update(id, dto));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanhiaAereaDTO> partialUpdate(@PathVariable Long id, @RequestBody CompanhiaAereaDTO dto) {
        return ResponseEntity.ok(companhiaAereaService.partialUpdate(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companhiaAereaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
