package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.CompanhiaAereaDTO;
import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.service.CompanhiaAereaService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companias")
@RequiredArgsConstructor
public class CompanhiaAereaController {

    private final CompanhiaAereaService companhiaAereaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanhiaAerea> save(@RequestBody CompanhiaAerea companhiaAerea) {
        return ResponseEntity.ok(companhiaAereaService.save(companhiaAerea));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompanhiaAerea>> findAll() {
        return ResponseEntity.ok(companhiaAereaService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanhiaAerea> findById(@PathVariable Long id) {
        return ResponseEntity.ok(companhiaAereaService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanhiaAerea> update(@PathVariable Long id, @RequestBody CompanhiaAerea companhiaAerea) {
        return ResponseEntity.ok(companhiaAereaService.update(id, companhiaAerea));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanhiaAerea> partialUpdate(@PathVariable Long id, @RequestBody CompanhiaAerea companhiaAerea) {
        return ResponseEntity.ok(companhiaAereaService.partialUpdate(id, companhiaAerea));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companhiaAereaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
