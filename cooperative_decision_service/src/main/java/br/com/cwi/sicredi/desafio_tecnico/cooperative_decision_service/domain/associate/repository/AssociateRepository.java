package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.repository;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AssociateRepository extends PagingAndSortingRepository<Associate, Long> {
}
