package br.pucpr.expedya.repository;

import br.pucpr.expedya.model.CompanhiaAerea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CompanhiaAereaRepository extends JpaRepository<CompanhiaAerea, Long> {
}

