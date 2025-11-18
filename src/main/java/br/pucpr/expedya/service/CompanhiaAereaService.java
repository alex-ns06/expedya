package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.CompanhiaAereaDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.mapper.MapperDTO;
import br.pucpr.expedya.model.Aviao;
import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.repository.AviaoRepository;
import br.pucpr.expedya.repository.CompanhiaAereaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanhiaAereaService {

    private final CompanhiaAereaRepository companhiaAereaRepository;
    private final AviaoRepository aviaoRepository;
    private final MapperDTO mapperDTO;

    // ---------------- CREATE ----------------
    public CompanhiaAereaDTO save(CompanhiaAereaDTO dto) {

        // Carregar aviões pelo ID
        Set<Aviao> avioes = dto.getAvioesId() == null ? Set.of() :
                dto.getAvioesId().stream()
                        .map(id -> aviaoRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + id)))
                        .collect(Collectors.toSet());

        CompanhiaAerea nova = mapperDTO.toEntity(dto);
        nova.setAvioes(avioes);

        CompanhiaAerea saved = companhiaAereaRepository.save(nova);
        return mapperDTO.toDTO(saved);
    }

    // ---------------- UPDATE (PUT) ----------------
    public CompanhiaAereaDTO update(Long id, CompanhiaAereaDTO dto) {

        CompanhiaAerea existente = companhiaAereaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Companhia aérea não encontrada: " + id));

        Set<Aviao> avioes = dto.getAvioesId() == null ? Set.of() :
                dto.getAvioesId().stream()
                        .map(aviaoId -> aviaoRepository.findById(aviaoId)
                                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + aviaoId)))
                        .collect(Collectors.toSet());

        existente.setNome(dto.getNome());
        existente.setCnpj(dto.getCnpj());
        existente.setAvioes(avioes);

        return mapperDTO.toDTO(companhiaAereaRepository.save(existente));
    }

    // ---------------- GET ALL ----------------
    public List<CompanhiaAereaDTO> findAll() {
        return companhiaAereaRepository.findAll()
                .stream()
                .map(mapperDTO::toDTO)
                .toList();
    }

    // ---------------- GET BY ID ----------------
    public CompanhiaAereaDTO findById(Long id) {
        CompanhiaAerea c = companhiaAereaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Companhia aérea não encontrada: " + id));
        return mapperDTO.toDTO(c);
    }

    // ---------------- DELETE ----------------
    public void delete(Long id) {
        if (!companhiaAereaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Companhia aérea não encontrada: " + id);
        }
        companhiaAereaRepository.deleteById(id);
    }

    // ---------------- PATCH ----------------
    public CompanhiaAereaDTO partialUpdate(Long id, CompanhiaAereaDTO dto) {

        CompanhiaAerea existente = companhiaAereaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Companhia aérea não encontrada: " + id));

        if (dto.getNome() != null) existente.setNome(dto.getNome());
        if (dto.getCnpj() != null) existente.setCnpj(dto.getCnpj());

        if (dto.getAvioesId() != null) {
            Set<Aviao> novos = dto.getAvioesId().stream()
                    .map(aid -> aviaoRepository.findById(aid)
                            .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + aid)))
                    .collect(Collectors.toSet());
            existente.setAvioes(novos);
        }

        return mapperDTO.toDTO(companhiaAereaRepository.save(existente));
    }
}
