package br.pucpr.expedya.repository;

import br.pucpr.expedya.model.Aviao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AviaoRepository extends JpaRepository<Aviao, Long> {
}