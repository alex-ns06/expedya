package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.PassagemDTO;
import br.pucpr.expedya.service.PassagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passagens")
@RequiredArgsConstructor
public class PassagemController {

    private final PassagemService passagemService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PassagemDTO> save(@RequestBody PassagemDTO dto) {
        return ResponseEntity.ok(passagemService.save(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PassagemDTO>> findAll() {
        return ResponseEntity.ok(passagemService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PassagemDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(passagemService.findById(Long.valueOf(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PassagemDTO> update(@PathVariable Integer id, @RequestBody PassagemDTO dto) {
        return ResponseEntity.ok(passagemService.update(Long.valueOf(id), dto));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PassagemDTO> partialUpdate(@PathVariable Integer id, @RequestBody PassagemDTO dto) {
        return ResponseEntity.ok(passagemService.partialUpdate(Long.valueOf(id), dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        passagemService.delete(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}
