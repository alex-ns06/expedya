package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.CompanhiaAereaDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.model.Aviao;
import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.repository.AviaoRepository;
import br.pucpr.expedya.repository.CompanhiaAereaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CompanhiaAereaService {

    private final CompanhiaAereaRepository companhiaAereaRepository;
    private final AviaoRepository aviaoRepository;

    // ============ CREATE ============
    public CompanhiaAereaDTO save(CompanhiaAereaDTO dto) {

        Aviao aviao = aviaoRepository.findById(dto.getAviaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + dto.getAviaoId()));

        CompanhiaAerea nova = toEntity(dto, Set.of(aviao));

        return toDTO(companhiaAereaRepository.save(nova));
    }

    // ============ UPDATE ============
    public CompanhiaAereaDTO update(Long id, CompanhiaAereaDTO dto) {

        CompanhiaAerea existente = companhiaAereaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Companhia não encontrada: " + id));

        Aviao aviao = aviaoRepository.findById(dto.getAviaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + dto.getAviaoId()));

        existente.setNome(dto.getNome());
        existente.setCnpj(dto.getCnpj());
        existente.setAvioes(Set.of(aviao));

        return toDTO(companhiaAereaRepository.save(existente));
    }

    // ============ GET ALL ============
    public List<CompanhiaAereaDTO> findAll() {
        return companhiaAereaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ============ GET BY ID ============
    public CompanhiaAereaDTO findById(Long id) {
        CompanhiaAerea companhia = companhiaAereaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Companhia não encontrada: " + id));
        return toDTO(companhia);
    }

    // ============ DELETE ============
    public void delete(Long id) {
        if (!companhiaAereaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Companhia não encontrada: " + id);
        }
        companhiaAereaRepository.deleteById(id);
    }

    // ============ DTO → ENTITY ============
    private CompanhiaAerea toEntity(CompanhiaAereaDTO dto, Set<Aviao> avioes) {
        CompanhiaAerea c = new CompanhiaAerea();
        c.setNome(dto.getNome());
        c.setCnpj(dto.getCnpj());
        c.setAvioes(avioes);
        return c;
    }

    // ============ ENTITY → DTO ============
    private CompanhiaAereaDTO toDTO(CompanhiaAerea c) {
        Long aviaoId = c.getAvioes().stream().findFirst().map(Aviao::getId).orElse(null);
        String modeloAviao = c.getAvioes().stream().findFirst().map(Aviao::getModelo).orElse(null);

        return new CompanhiaAereaDTO(
                c.getId(),
                c.getNome(),
                c.getCnpj(),
                null,   // removido porque passagensId não pertence mais à entidade
                aviaoId,
                modeloAviao
        );
    }
}
