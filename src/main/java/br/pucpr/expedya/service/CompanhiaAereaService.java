package br.pucpr.expedya.service;

import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.repository.CompanhiaAereaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanhiaAereaService {
    private final CompanhiaAereaRepository companhiaAereaRepository;

    public CompanhiaAerea save(CompanhiaAerea companhiaAerea) {
        return companhiaAereaRepository.save(companhiaAerea);
    }

    public List<CompanhiaAerea> findAll() {
        return companhiaAereaRepository.findAll();
    }

    public void delete(Integer id) {
        companhiaAereaRepository.deleteById(id);
    }
}
