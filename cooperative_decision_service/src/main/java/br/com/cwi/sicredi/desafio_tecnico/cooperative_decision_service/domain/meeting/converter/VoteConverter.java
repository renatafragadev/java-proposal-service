package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.converter.AssociateConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.VoteModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.VoteRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Vote;

import java.util.Collection;
import java.util.stream.Collectors;

public class VoteConverter {

    public static Vote toEntity(VoteRequest voteRequest, Session session) {
        Vote vote = new Vote();
        vote.setDecision(voteRequest.getDecision());
        vote.setSeem(voteRequest.getSeem());
        vote.setAssociate(AssociateConverter.withId(voteRequest.getAssociateId()));
        vote.setSession(session);

        return vote;
    }

    public static VoteModel toModel(Vote vote) {
        VoteModel voteModel = new VoteModel();
        voteModel.setId(vote.getId());
        voteModel.setDecision(vote.getDecision());
        voteModel.setSeem(vote.getSeem());
        voteModel.setAssociate(AssociateConverter.toModel(vote.getAssociate()));

        return voteModel;
    }

    public static Collection<VoteModel> toListModel(Collection<Vote> voteList) {
        return voteList.stream().map(VoteConverter::toModel).collect(Collectors.toList());
    }
}
