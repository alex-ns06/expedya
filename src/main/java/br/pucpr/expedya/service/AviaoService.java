package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.AviaoDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.model.Aviao;
import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.model.Passagem;
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
public class AviaoService {

    private final AviaoRepository aviaoRepository;
    private final CompanhiaAereaRepository companhiaAereaRepository;
    private final PassagemRepository passagemRepository;

    // =============== CREATE ===============
    public AviaoDTO save(AviaoDTO dto) {

        Set<CompanhiaAerea> companhias =
                dto.getCompanhiaAereaId().stream()
                        .map(id -> companhiaAereaRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Companhia aérea não encontrada: " + id)))
                        .collect(Collectors.toSet());

        Set<Passagem> passagens =
                dto.getPassagemId().stream()
                        .map(pid -> passagemRepository.findById(pid.intValue())
                                .orElseThrow(() -> new ResourceNotFoundException("Passagem não encontrada: " + pid)))
                        .collect(Collectors.toSet());

        Aviao aviao = toEntity(dto, companhias, passagens);
        Aviao saved = aviaoRepository.save(aviao);

        return toDTO(saved);
    }

    // =============== UPDATE ===============
    public AviaoDTO update(Long id, AviaoDTO dto) {

        Aviao existente = aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + id));

        Set<CompanhiaAerea> companhias =
                dto.getCompanhiaAereaId().stream()
                        .map(cid -> companhiaAereaRepository.findById(cid)
                                .orElseThrow(() -> new ResourceNotFoundException("Companhia aérea não encontrada: " + cid)))
                        .collect(Collectors.toSet());

        Set<Passagem> passagens =
                dto.getPassagemId().stream()
                        .map(pid -> passagemRepository.findById(pid.intValue())
                                .orElseThrow(() -> new ResourceNotFoundException("Passagem não encontrada: " + pid)))
                        .collect(Collectors.toSet());

        existente.setModelo(dto.getModelo());
        existente.setCodigoAeronave(dto.getCodigoAeronave());
        existente.setCapacidadePassageiros(dto.getCapacidadePassageiros());
        existente.setCompanhias(companhias);
        existente.setPassagem(passagens);

        return toDTO(aviaoRepository.save(existente));
    }

    // =============== GET ALL ===============
    public List<AviaoDTO> findAll() {
        return aviaoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // =============== GET BY ID ===============
    public AviaoDTO findById(Long id) {
        Aviao aviao = aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + id));
        return toDTO(aviao);
    }

    // =============== DELETE ===============
    public void delete(Long id) {
        Aviao aviao = aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado: " + id));
        aviaoRepository.delete(aviao);
    }

    // =============== DTO → ENTITY ===============
    private Aviao toEntity(AviaoDTO dto, Set<CompanhiaAerea> companhias, Set<Passagem> passagens) {
        Aviao aviao = new Aviao();
        aviao.setModelo(dto.getModelo());
        aviao.setCodigoAeronave(dto.getCodigoAeronave());
        aviao.setCapacidadePassageiros(dto.getCapacidadePassageiros());
        aviao.setCompanhias(companhias);
        aviao.setPassagem(passagens);
        return aviao;
    }

    // =============== ENTITY → DTO ===============
    private AviaoDTO toDTO(Aviao aviao) {
        return new AviaoDTO(
                aviao.getId(),
                aviao.getModelo(),
                aviao.getCodigoAeronave(),
                aviao.getCapacidadePassageiros(),
                aviao.getCompanhias().stream().map(CompanhiaAerea::getId).collect(Collectors.toSet()),
                aviao.getCompanhias().stream().map(CompanhiaAerea::getNome).collect(Collectors.toSet()),
                aviao.getPassagem().stream().map(Passagem::getId).collect(Collectors.toSet())
        );
    }
}
