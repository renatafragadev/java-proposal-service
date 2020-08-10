package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.repository;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AssociateRepository extends PagingAndSortingRepository<Associate, Long> {

    boolean existsByDocument(String document);

    Optional<Associate> findByUuid(String uuid);
}
