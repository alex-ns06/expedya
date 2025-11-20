package br.pucpr.expedya.controller;

import br.pucpr.expedya.config.GlobalApiResponses;
import br.pucpr.expedya.dto.ClienteDTO;
import br.pucpr.expedya.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@GlobalApiResponses
@SecurityRequirement(name = "Bearer Authentication")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // GET - Buscar todos (SÓ ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ClienteDTO> findAll() {
        return clienteService.findAll();
    }

    // GET - Buscar por ID (ADMIN ou o Próprio Usuário)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @clienteSecurity.checkId(authentication, #id)") // MUDANÇA (Requer Bean de segurança)
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) { // MUDANÇA (Long)
        return ResponseEntity.ok(clienteService.findById(id)); // MUDANÇA
    }

    // POST - Cadastrar novo cliente (PÚBLICO - Já configurado no SecurityConfig)
    @Operation(summary = "Cria um cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado")
    })
    @PostMapping
    public ResponseEntity<ClienteDTO> save(@RequestBody ClienteDTO cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente)); // MUDANÇA
    }

    // PUT - Atualizar cliente completo (ADMIN ou o Próprio Usuário)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @clienteSecurity.checkId(authentication, #id)") // MUDANÇA
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteDTO clienteAtualizado) { // MUDANÇA (Long)
        return ResponseEntity.ok(clienteService.update(id, clienteAtualizado)); // MUDANÇA
    }

    // PATCH - Atualizar parcialmente (ADMIN ou o Próprio Usuário)
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @clienteSecurity.checkId(authentication, #id)") // MUDANÇA
    public ResponseEntity<ClienteDTO> partialUpdate(@PathVariable Long id, @RequestBody ClienteDTO clienteParcial) { // MUDANÇA (Long)
        return ResponseEntity.ok(clienteService.partialUpdate(id, clienteParcial)); // MUDANÇA
    }

    // DELETE - Excluir cliente (SÓ ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // MUDANÇA
    public ResponseEntity<Void> delete(@PathVariable Long id) { // MUDANÇA (Long)
        clienteService.delete(id); // MUDANÇA
        return ResponseEntity.noContent().build();
    }

}