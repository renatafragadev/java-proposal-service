package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Vote;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.enumerator.DecisionType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VoteRepository extends PagingAndSortingRepository<Vote, Long>, JpaSpecificationExecutor<Vote> {

    boolean existsByAssociateIdAndSessionId(Long associateId, Long sessionId);

    int countBySessionAndDecision(Session session, DecisionType decision);

    int countBySession(Session session);

}
