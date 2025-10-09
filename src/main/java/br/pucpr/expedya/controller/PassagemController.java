package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.PassagemDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/passagens")
@SecurityRequirement(name = "Bearer Authentication")
public class PassagemController {
    private List<PassagemDTO> passagens = new ArrayList<>();

    // GET - Buscar todos
    @GetMapping
    public List<PassagemDTO> findAll() { return passagens; }

    // GET - Buscar por ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PassagemDTO> findById(@PathVariable String id) {
        return passagens.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST - Cadastrar nova passagem
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PassagemDTO> save(@RequestBody PassagemDTO passagem) {
        passagens.add(passagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(passagem);
    }

    // PUT - Atualizar passagem completa
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PassagemDTO> update(@PathVariable String id, @RequestBody PassagemDTO passagemAtualizada) {
        for (int i = 0; i < passagens.size(); i++) {
            if (passagens.get(i).getId().equals(id)) {
                passagens.set(i, passagemAtualizada);
                return ResponseEntity.ok(passagemAtualizada);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Atualizar passagem parcialmente
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<PassagemDTO> partialUpdate(@PathVariable String id, @RequestBody PassagemDTO passagemParcial) {
        for (PassagemDTO p : passagens) {
            if (p.getId().equals(id)) {
                BeanUtils.copyProperties(passagemParcial, p, getNullPropertyNames(passagemParcial));
                return ResponseEntity.ok(p);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE - Excluir passagem
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<PassagemDTO> delete(@PathVariable String id) {
        boolean removed = passagens.removeIf(p -> p.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.notFound().build(); // 404
    }

    // Retorna os nomes das propriedades nulas de um objeto
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        return emptyNames.toArray(new String[0]);
    }
}
