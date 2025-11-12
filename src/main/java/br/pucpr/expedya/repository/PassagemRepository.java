package br.pucpr.expedya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.pucpr.expedya.model.Passagem;

public interface PassagemRepository extends JpaRepository<Passagem, Long> {
}
