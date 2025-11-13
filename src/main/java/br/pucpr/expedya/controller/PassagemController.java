package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.PassagemDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.model.Passagem;
import br.pucpr.expedya.service.PassagemService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
    private final PassagemService passagemService;

    public PassagemController(PassagemService passagemService) {
        this.passagemService = passagemService;
    }

    // POST - Cadastrar nova Passagem
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Passagem criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Os dados da Passagem são inválidos")
    })
    public ResponseEntity<PassagemDTO> save(@Valid @RequestBody PassagemDTO passagemDTO) {
        Passagem passagem = new ModelMapper().map(passagemDTO, Passagem.class);
        passagemService.save(passagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(passagemDTO);
    }

    // GET - Buscar todas as Passagens
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Passagens retornada com sucesso")
    })
    public ResponseEntity<List<PassagemDTO>> findAll() {
        List<Passagem> passagens = passagemService.findAll();
        List<PassagemDTO> passagensDTO = passagens.stream()
                .map(passagem -> new ModelMapper().map(passagem, PassagemDTO.class))
                .toList();
        return new ResponseEntity<>(passagensDTO, HttpStatus.OK);
    }

    // PUT - Atualizar Passagem
    @PutMapping("/{id}")
    public ResponseEntity<PassagemDTO> update(@PathVariable("id") Integer id, @RequestBody PassagemDTO passagemDTO) {
//        throws ResourceNotFoundException {
//            if (id == null || passagemDTO.getId() == null) {
//                throw new ResourceNotFoundException("O ID é necessário");
//            }
//        }

        Passagem passagem = new ModelMapper().map(passagemDTO, Passagem.class);
        passagemService.save(passagem);

        return ResponseEntity.status(HttpStatus.CREATED).body(passagemDTO);
    }

    // DELETE - Deletar Passagem
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        passagemService.delete(id);
    }
}
