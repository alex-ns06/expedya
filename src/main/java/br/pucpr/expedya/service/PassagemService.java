package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.PassagemDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.model.Passagem;
import br.pucpr.expedya.repository.PassagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PassagemService {

    private final PassagemRepository passagemRepository;

    // -------- CREATE --------
    public PassagemDTO save(PassagemDTO dto) {
        Passagem passagem = toEntity(dto);
        return toDTO(passagemRepository.save(passagem));
    }

    // -------- GET ALL --------
    public List<PassagemDTO> findAll() {
        return passagemRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // -------- GET BY ID --------
    public PassagemDTO findById(Integer id) {
        Passagem passagem = passagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passagem não encontrada com id: " + id));
        return toDTO(passagem);
    }

    // -------- DELETE --------
    public void delete(Integer id) {
        passagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passagem não encontrada com id: " + id));
        passagemRepository.deleteById(id);
    }

    // -------- UPDATE (PUT) --------
    public PassagemDTO update(Integer id, PassagemDTO dto) {
        Passagem existing = passagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passagem não encontrada com id: " + id));

        Passagem updated = toEntity(dto);
        updated.setId(id);

        return toDTO(passagemRepository.save(updated));
    }

    // -------- UPDATE PARCIAL (PATCH) --------
    public PassagemDTO partialUpdate(Integer id, PassagemDTO dto) {
        Passagem passagem = passagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passagem não encontrada com id: " + id));

        BeanUtils.copyProperties(dto, passagem, getNullProperties(dto));

        return toDTO(passagemRepository.save(passagem));
    }

    // --- DTO → ENTITY ---
    private Passagem toEntity(PassagemDTO dto) {
        Passagem passagem = new Passagem();
        BeanUtils.copyProperties(dto, passagem);
        return passagem;
    }

    // --- ENTITY → DTO ---
    private PassagemDTO toDTO(Passagem passagem) {
        PassagemDTO dto = new PassagemDTO();
        BeanUtils.copyProperties(passagem, dto);
        return dto;
    }

    private String[] getNullProperties(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> empty = new HashSet<>();

        for (var pd : src.getPropertyDescriptors()) {
            Object value = src.getPropertyValue(pd.getName());
            if (value == null) empty.add(pd.getName());
        }
        return empty.toArray(new String[0]);
    }
}
