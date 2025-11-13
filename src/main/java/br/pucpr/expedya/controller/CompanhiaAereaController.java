package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.CompanhiaAereaDTO;
import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.service.CompanhiaAereaService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companhias-aereas")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')") // Protegendo todo o controller para ADMIN
public class CompanhiaAereaController {
    private final CompanhiaAereaService companhiaAereaService;

    public CompanhiaAereaController(CompanhiaAereaService companhiaAereaService) {
        this.companhiaAereaService = companhiaAereaService;
    }

    // POST - Cadastrar nova Companhia Aérea
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Companhia Aérea criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Os dados da Companhia Aérea são inválidos")
    })
    public ResponseEntity<CompanhiaAereaDTO> save(@Valid @RequestBody CompanhiaAereaDTO companhiaAereaDTO) {
        CompanhiaAerea companhiaAerea = new ModelMapper().map(companhiaAereaDTO, CompanhiaAerea.class);
        companhiaAereaService.save(companhiaAerea);
        return ResponseEntity.status(HttpStatus.CREATED).body(companhiaAereaDTO);
    }

    // GET - Buscar todas as Companhias Aéreas
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Companhias Aéreas retornada com sucesso")
    })
    public ResponseEntity<List<CompanhiaAereaDTO>> findAll() {
        List<CompanhiaAerea> companhiasAereas = companhiaAereaService.findAll();
        List<CompanhiaAereaDTO> companhiaAereaDTOS = companhiasAereas.stream()
                .map(companhiaAerea -> new ModelMapper().map(companhiaAerea, CompanhiaAereaDTO.class))
                .toList();
        return new ResponseEntity<>(companhiaAereaDTOS, HttpStatus.OK);
    }

    // PUT - Atualizar Companhia Aérea
    @PutMapping("/{id}")
    public ResponseEntity<CompanhiaAereaDTO> update(@PathVariable("id") Integer id, @RequestBody CompanhiaAereaDTO companhiaAereaDTO) {
//        throws ResourceNotFoundException {
//            if (id == null || companhiaAereaDTO.getId() == null) {
//                throw new ResourceNotFoundException("O ID é necessário");
//            }
//        }

        CompanhiaAerea companhiaAerea = new ModelMapper().map(companhiaAereaDTO, CompanhiaAerea.class);
        companhiaAereaService.save(companhiaAerea);

        return ResponseEntity.status(HttpStatus.CREATED).body(companhiaAereaDTO);
    }

    // DELETE - Excluir Companhia Aérea
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        companhiaAereaService.delete(id);
    }
}
