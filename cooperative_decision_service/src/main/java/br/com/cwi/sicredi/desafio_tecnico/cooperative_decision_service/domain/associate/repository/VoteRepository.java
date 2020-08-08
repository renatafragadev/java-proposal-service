package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.repository;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Vote;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VoteRepository extends PagingAndSortingRepository<Vote, Long>, JpaSpecificationExecutor<Vote> {

    boolean existsByAssociateIdAndSessionId(Long associateId, Long sessionId);
}
