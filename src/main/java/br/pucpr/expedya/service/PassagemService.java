package br.pucpr.expedya.service;

import br.pucpr.expedya.model.Passagem;
import br.pucpr.expedya.repository.PassagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PassagemService {
    private final PassagemRepository passagemRepository;

    public Passagem save(Passagem passagem) {
        return passagemRepository.save(passagem);
    }

    public List<Passagem> findAll() {
        return passagemRepository.findAll();
    }

    public void delete(Integer id) {
        passagemRepository.deleteById(id);
    }
}
