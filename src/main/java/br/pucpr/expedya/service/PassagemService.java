package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.PassagemDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.mapper.MapperDTO;
import br.pucpr.expedya.model.Aviao;
import br.pucpr.expedya.model.Cliente;
import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.model.Passagem;
import br.pucpr.expedya.repository.AviaoRepository;
import br.pucpr.expedya.repository.ClienteRepository;
import br.pucpr.expedya.repository.CompanhiaAereaRepository;
import br.pucpr.expedya.repository.PassagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PassagemService {

    private final PassagemRepository passagemRepository;

    // Injetamos os repositórios para buscar as entidades pai
    private final CompanhiaAereaRepository companhiaAereaRepository;
    private final AviaoRepository aviaoRepository;
    private final ClienteRepository clienteRepository;

    private final MapperDTO mapperDTO;

    // -------- CREATE --------
    public PassagemDTO save(PassagemDTO dto) {

        // 1. Validar Companhia (já fizemos isso)
        if (dto.getCompanhiaAereaId() == null) throw new IllegalArgumentException("Companhia obrigatória");
        CompanhiaAerea companhia = companhiaAereaRepository.findById(dto.getCompanhiaAereaId())
                .orElseThrow(() -> new ResourceNotFoundException("Companhia não encontrada"));

        // 2. Validar Cliente (NOVA LÓGICA)
        if (dto.getClienteId() == null) throw new IllegalArgumentException("Cliente obrigatório");

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + dto.getClienteId()));

        // 3. Montar Entidade
        Passagem entity = mapperDTO.toEntity(dto);
        entity.setCompanhiaAerea(companhia);
        entity.setCliente(cliente); // <--- Vincula o cliente à passagem

        // 4. Validar Avião (opcional)
        if (dto.getAviaoId() != null) {
            Aviao aviao = aviaoRepository.findById(dto.getAviaoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado"));
            entity.setAviao(aviao);
        }

        return mapperDTO.toDTO(passagemRepository.save(entity));
    }

    // -------- UPDATE (PUT) --------
    public PassagemDTO update(Long id, PassagemDTO dto) {
        Passagem existente = passagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passagem não encontrada com id: " + id));

        // Atualiza dados básicos via mapper ou setters manuais se preferir
        Passagem atualizada = mapperDTO.toEntity(dto);
        atualizada.setId(id);

        // No PUT, precisamos garantir que as relações sejam mantidas ou atualizadas
        if (dto.getCompanhiaAereaId() != null) {
            CompanhiaAerea companhia = companhiaAereaRepository.findById(dto.getCompanhiaAereaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Companhia não encontrada"));
            atualizada.setCompanhiaAerea(companhia);
        } else {
            // Mantém a companhia antiga se não foi enviada (ou lança erro, dependendo da sua regra)
            atualizada.setCompanhiaAerea(existente.getCompanhiaAerea());
        }

        if (dto.getAviaoId() != null) {
            Aviao aviao = aviaoRepository.findById(dto.getAviaoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado"));
            atualizada.setAviao(aviao);
        }

        return mapperDTO.toDTO(passagemRepository.save(atualizada));
    }

    // -------- GET ALL --------
    public List<PassagemDTO> findAll() {
        return passagemRepository.findAll()
                .stream()
                .map(mapperDTO::toDTO)
                .toList();
    }

    // -------- GET BY ID --------
    public PassagemDTO findById(Long id) {
        Passagem passagem = passagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passagem não encontrada com id: " + id));
        return mapperDTO.toDTO(passagem);
    }

    // -------- DELETE --------
    public void delete(Long id) {
        if (!passagemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Passagem não encontrada com id: " + id);
        }
        passagemRepository.deleteById(id);
    }

    // -------- PATCH --------
    public PassagemDTO partialUpdate(Long id, PassagemDTO dto) {
        Passagem existente = passagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passagem não encontrada com id: " + id));

        if (dto.getOrigem() != null) existente.setOrigem(dto.getOrigem());
        if (dto.getDestino() != null) existente.setDestino(dto.getDestino());
        if (dto.getDataPartida() != null) existente.setDataPartida(dto.getDataPartida());
        if (dto.getHoraPartida() != null) existente.setHoraPartida(dto.getHoraPartida());
        if (dto.getAssento() != null) existente.setAssento(dto.getAssento());
        if (dto.getClasse() != null) existente.setClasse(dto.getClasse());

        // Atualiza Avião
        if (dto.getAviaoId() != null) {
            Aviao aviao = aviaoRepository.findById(dto.getAviaoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + dto.getAviaoId()));
            existente.setAviao(aviao);
        }

        // Atualiza Companhia (caso seja permitido mudar de companhia)
        if (dto.getCompanhiaAereaId() != null) {
            CompanhiaAerea companhia = companhiaAereaRepository.findById(dto.getCompanhiaAereaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Companhia não encontrada"));
            existente.setCompanhiaAerea(companhia);
        }

        return mapperDTO.toDTO(passagemRepository.save(existente));
    }
}