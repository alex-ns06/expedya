package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.ClienteDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.*;

@RestController
@RequestMapping("/api/v1/clientes")
@SecurityRequirement(name = "Bearer Authentication")
public class ClienteController {

    private List<ClienteDTO> clientes = new ArrayList<>();

    // GET - Buscar todos
    @GetMapping
    public List<ClienteDTO> findAll() {
        return clientes;
    }

    // GET - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
        return clientes.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Cadastrar novo cliente
    @PostMapping
    public ResponseEntity<ClienteDTO> save(@RequestBody ClienteDTO cliente) {
        clientes.add(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    // PUT - Atualizar cliente completo
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @RequestBody ClienteDTO clienteAtualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId().equals(id)) {
                clientes.set(i, clienteAtualizado);
                return ResponseEntity.ok(clienteAtualizado);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Atualizar parcialmente
    @PatchMapping("/{id}")
    public ResponseEntity<ClienteDTO> partialUpdate(@PathVariable Integer id, @RequestBody ClienteDTO clienteParcial) {
        for (ClienteDTO c : clientes) {
            if (c.getId().equals(id)) {
                BeanUtils.copyProperties(clienteParcial, c, getNullPropertyNames(clienteParcial));
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE - Excluir cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean removed = clientes.removeIf(c -> c.getId().equals(id));
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
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
}
