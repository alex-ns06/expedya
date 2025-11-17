package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.AviaoDTO;
import br.pucpr.expedya.model.Aviao;
import br.pucpr.expedya.service.AviaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avioes")
@RequiredArgsConstructor
public class AviaoController {

    private final AviaoService aviaoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Aviao> save(@RequestBody Aviao aviao) {
        return ResponseEntity.ok(aviaoService.save(aviao));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Aviao>> findAll() {
        return ResponseEntity.ok(aviaoService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Aviao> findById(@PathVariable Long id) {
        return ResponseEntity.ok(aviaoService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Aviao> update(@PathVariable Long id, @RequestBody Aviao aviao) {
        return ResponseEntity.ok(aviaoService.update(id, aviao));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Aviao> partialUpdate(@PathVariable Long id, @RequestBody Aviao aviao) {
        return ResponseEntity.ok(aviaoService.partialUpdate(id, aviao));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aviaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
