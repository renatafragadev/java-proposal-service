package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.request.VoteRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Vote;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter.SessionConverter;

public class VoteConverter {

    public static Vote toEntity(VoteRequest voteRequest) {
        Vote vote = new Vote();
        vote.setDecision(voteRequest.getDecision());
        vote.setSeem(voteRequest.getSeem());
        vote.setAssociate(AssociateConverter.withId(voteRequest.getAssociateId()));
        vote.setSession(SessionConverter.withId(voteRequest.getSessionId()));

        return vote;
    }
}
