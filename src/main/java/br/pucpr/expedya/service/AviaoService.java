package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.AviaoDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.mapper.MapperDTO;
import br.pucpr.expedya.model.Aviao;
import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.repository.AviaoRepository;
import br.pucpr.expedya.repository.CompanhiaAereaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AviaoService {

    private final AviaoRepository aviaoRepository;
    private final CompanhiaAereaRepository companhiaAereaRepository;
    private final MapperDTO mapperDTO;

    // =============== CREATE ===============
    public AviaoDTO save(AviaoDTO dto) {

        CompanhiaAerea companhia =
                companhiaAereaRepository.findById(dto.getCompanhiaAereaId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Companhia aérea não encontrada: " + dto.getCompanhiaAereaId()));

        Aviao aviao = mapperDTO.toEntity(dto);
        aviao.setCompanhiaAerea(companhia);

        Aviao saved = aviaoRepository.save(aviao);
        return mapperDTO.toDTO(saved);
    }

    // =============== UPDATE COMPLETO ===============
    public AviaoDTO update(Long id, AviaoDTO dto) {

        Aviao existente = aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + id));

        CompanhiaAerea companhia =
                companhiaAereaRepository.findById(dto.getCompanhiaAereaId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Companhia aérea não encontrada: " + dto.getCompanhiaAereaId()));

        existente.setModelo(dto.getModelo());
        existente.setCodigoAeronave(dto.getCodigoAeronave());
        existente.setCapacidadePassageiros(dto.getCapacidadePassageiros());
        existente.setCompanhiaAerea(companhia);

        Aviao atualizado = aviaoRepository.save(existente);
        return mapperDTO.toDTO(atualizado);
    }

    // =============== UPDATE PARCIAL (PATCH) ===============
    public AviaoDTO partialUpdate(Long id, AviaoDTO dto) {

        Aviao existente = aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + id));

        // Atualiza somente atributos não nulos
        if (dto.getModelo() != null) {
            existente.setModelo(dto.getModelo());
        }

        if (dto.getCodigoAeronave() != null) {
            existente.setCodigoAeronave(dto.getCodigoAeronave());
        }

        if (dto.getCapacidadePassageiros() != null) {
            existente.setCapacidadePassageiros(dto.getCapacidadePassageiros());
        }

        // Companhia aérea (opcional no PATCH)
        if (dto.getCompanhiaAereaId() != null) {
            CompanhiaAerea companhia =
                    companhiaAereaRepository.findById(dto.getCompanhiaAereaId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Companhia aérea não encontrada: " + dto.getCompanhiaAereaId()));
            existente.setCompanhiaAerea(companhia);
        }

        Aviao atualizado = aviaoRepository.save(existente);

        return mapperDTO.toDTO(atualizado);
    }

    // =============== GET ALL ===============
    public List<AviaoDTO> findAll() {
        return aviaoRepository.findAll()
                .stream()
                .map(mapperDTO::toDTO)
                .toList();
    }

    // =============== GET BY ID ===============
    public AviaoDTO findById(Long id) {
        Aviao aviao = aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + id));
        return mapperDTO.toDTO(aviao);
    }

    // =============== DELETE ===============
    public void delete(Long id) {
        Aviao aviao = aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + id));
        aviaoRepository.delete(aviao);
    }
}
