package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.PassagemDTO;
import br.pucpr.expedya.model.Passagem;
import br.pucpr.expedya.service.PassagemService;
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

import java.util.*;

@RestController
@RequestMapping("/passagens")
@RequiredArgsConstructor
public class PassagemController {

    private final PassagemService passagemService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Passagem> save(@RequestBody Passagem passagem) {
        return ResponseEntity.ok(passagemService.save(passagem));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Passagem>> findAll() {
        return ResponseEntity.ok(passagemService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Passagem> findById(@PathVariable Integer id) {
        return passagemService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Passagem> update(@PathVariable Integer id, @RequestBody Passagem passagem) {
        passagem.setId(id);
        return ResponseEntity.ok(passagemService.save(passagem));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Passagem> partialUpdate(@PathVariable Integer id, @RequestBody Passagem passagem) {
        return ResponseEntity.ok(passagemService.partialUpdate(id, passagem));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        passagemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
