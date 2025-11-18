package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.PassagemDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.mapper.MapperDTO;
import br.pucpr.expedya.model.Passagem;
import br.pucpr.expedya.repository.PassagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PassagemService {

    private final PassagemRepository passagemRepository;
    private final MapperDTO mapperDTO;

    // -------- CREATE --------
    public PassagemDTO save(PassagemDTO dto) {
        Passagem entity = mapperDTO.toEntity(dto);
        return mapperDTO.toDTO(passagemRepository.save(entity));
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

    // -------- UPDATE (PUT) --------
    public PassagemDTO update(Long id, PassagemDTO dto) {

        passagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passagem não encontrada com id: " + id));

        Passagem updated = mapperDTO.toEntity(dto);
        updated.setId(id);

        return mapperDTO.toDTO(passagemRepository.save(updated));
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

        // Se quiser atualizar o avião no PATCH
        if (dto.getAviaoId() != null) {
            existente.setAviao( mapperDTO.getAviaoById(dto.getAviaoId()) );
        }

        return mapperDTO.toDTO(passagemRepository.save(existente));
    }
}
