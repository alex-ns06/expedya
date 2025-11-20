package br.pucpr.expedya.controller;

import br.pucpr.expedya.config.GlobalApiResponses;
import br.pucpr.expedya.dto.ClienteDTO;
import br.pucpr.expedya.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "cliente-controller", description = "Gerencia o cadastro, consulta e atualização de clientes.")
@GlobalApiResponses
@SecurityRequirement(name = "Bearer Authentication")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista todos os clientes", description = "Retorna todos os clientes cadastrados. Apenas administradores podem acessar.")
    public List<ClienteDTO> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @clienteSecurity.checkId(authentication, #id)")
    @Operation(summary = "Busca cliente por ID", description = "Retorna um cliente específico. Acesso permitido para ADMIN ou o próprio usuário.")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo cliente", description = "Realiza o cadastro de um novo cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado")
    })
    public ResponseEntity<ClienteDTO> save(@RequestBody ClienteDTO cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @clienteSecurity.checkId(authentication, #id)")
    @Operation(summary = "Atualiza cliente", description = "Atualiza completamente os dados de um cliente.")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteDTO clienteAtualizado) {
        return ResponseEntity.ok(clienteService.update(id, clienteAtualizado));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @clienteSecurity.checkId(authentication, #id)")
    @Operation(summary = "Atualização parcial de cliente", description = "Atualiza somente alguns campos do cliente.")
    public ResponseEntity<ClienteDTO> partialUpdate(@PathVariable Long id, @RequestBody ClienteDTO clienteParcial) {
        return ResponseEntity.ok(clienteService.partialUpdate(id, clienteParcial));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove cliente", description = "Exclui permanentemente o cliente. Apenas administradores podem executar.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
