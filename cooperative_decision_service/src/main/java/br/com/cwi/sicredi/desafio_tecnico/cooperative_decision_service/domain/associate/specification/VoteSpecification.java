package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.specification;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate_;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Vote;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session_;
import org.springframework.data.jpa.domain.Specification;

public class VoteSpecification {

    public static Specification<Vote> byAssociateId(Long associateId) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Associate_.ID), associateId));
    }

    public static Specification<Vote> bySessionId(Long sessionId) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Session_.ID), sessionId));
    }
}
