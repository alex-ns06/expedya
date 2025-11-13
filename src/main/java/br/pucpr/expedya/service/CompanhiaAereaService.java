package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.CompanhiaAereaDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.model.Aviao;
import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.repository.AviaoRepository;
import br.pucpr.expedya.repository.CompanhiaAereaRepository;
import br.pucpr.expedya.repository.PassagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanhiaAereaService {
    private final CompanhiaAereaRepository companhiaAereaRepository;
    private final PassagemRepository passagemRepository;
    private final AviaoRepository aviaoRepository;

    public CompanhiaAerea save(CompanhiaAereaDTO companhiaAereaDTO) {
        Aviao aviao = aviaoRepository.findById((long) Math.toIntExact(companhiaAereaDTO.getAviaoId()))
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado com id: " + companhiaAereaDTO.getAviaoId()));

        CompanhiaAerea companhiaAerea = toEntity(companhiaAereaDTO, aviao);
        CompanhiaAerea savedCompanhiaAerea = companhiaAereaRepository.save(companhiaAerea);
        return toDTO(savedCompanhiaAerea);
    }

    public List<CompanhiaAerea> findAll() {
        return companhiaAereaRepository.findAll();
    }

    public void delete(Integer id) {
        companhiaAereaRepository.deleteById(id);
    }

    private CompanhiaAereaDTO toDTO(CompanhiaAerea companhiaAerea) {
        return new CompanhiaAereaDTO(
                companhiaAerea.getId(),
                companhiaAerea.getNome(),
                companhiaAerea.getCnpj(),
                companhiaAerea.getPassagens(),
                companhiaAerea.getAvioes().stream().map(Aviao::getId).collect(Collectors.toSet()),
                companhiaAerea.getAvioes().stream().map(Aviao::getModelo).collect(Collectors.toSet())
        );
    }

    private CompanhiaAerea toEntity(CompanhiaAereaDTO dto, Aviao aviao) {
        CompanhiaAerea companhiaAerea = new CompanhiaAerea();
        companhiaAerea.setNome(dto.getNome());
        companhiaAerea.setCnpj(dto.getCnpj());
        companhiaAerea.setPassagensId(dto.getPassagensId());
        companhiaAerea.setAvioes(Set.of(aviao));
        return companhiaAerea;
    }
}
