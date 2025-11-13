package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.AviaoDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.model.Aviao;
import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.model.Passagem;
import br.pucpr.expedya.repository.AviaoRepository;
import br.pucpr.expedya.repository.CompanhiaAereaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AviaoService {

    private final AviaoRepository aviaoRepository;
    private final CompanhiaAereaRepository companhiaAereaRepository; // Para integração

    public AviaoDTO save(AviaoDTO dto) {
        CompanhiaAerea companhia = companhiaAereaRepository.findById(Math.toIntExact(dto.getCompanhiaAereaId()))
                .orElseThrow(() -> new ResourceNotFoundException("CompanhiaAerea não encontrada com id: " + dto.getCompanhiaAereaId()));

        Aviao aviao = toEntity(dto, companhia);
        Aviao savedAviao = aviaoRepository.save(aviao);
        return toDTO(savedAviao);
    }

    public AviaoDTO update(Long id, AviaoDTO dto) {
        aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aviao não encontrado com id: " + id));

        CompanhiaAerea companhia = companhiaAereaRepository.findById(Math.toIntExact(dto.getCompanhiaAereaId()))
                .orElseThrow(() -> new ResourceNotFoundException("CompanhiaAerea não encontrada com id: " + dto.getCompanhiaAereaId()));

        Aviao aviao = toEntity(dto, companhia);
        aviao.setId(id); // Garante que é uma atualização
        Aviao updatedAviao = aviaoRepository.save(aviao);
        return toDTO(updatedAviao);
    }

    public List<AviaoDTO> findAll() {
        return aviaoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AviaoDTO findById(Long id) {
        Aviao aviao = aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aviao não encontrado com id: " + id));
        return toDTO(aviao);
    }

    public void delete(Long id) {
        Aviao aviao = aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aviao não encontrado com id: " + id));
        aviaoRepository.delete(aviao);
    }

    // --- Mapeadores DTO <-> Entity ---

    private AviaoDTO toDTO(Aviao aviao) {
        return new AviaoDTO(
                aviao.getId(),
                aviao.getModelo(),
                aviao.getCodigoAeronave(),
                aviao.getCapacidadePassageiros(),
                aviao.getCompanhias().stream()
                        .map(CompanhiaAerea::getId)
                        .collect(Collectors.toSet()),
                aviao.getCompanhias().stream()
                        .map(CompanhiaAerea::getNome)
                        .collect(Collectors.toSet()),
                aviao.getPassagem().stream()
                        .map(Passagem::getId)
                        .collect(Collectors.toSet())
        );
    }

    private Aviao toEntity(AviaoDTO dto, CompanhiaAerea companhia) {
        Aviao aviao = new Aviao();
        aviao.setModelo(dto.getModelo());
        aviao.setCodigoAeronave(dto.getCodigoAeronave());
        aviao.setCapacidadePassageiros(dto.getCapacidadePassageiros());

        if (companhia != null) {
            aviao.getCompanhias().add(companhia);
        }

        return aviao;
    }
}