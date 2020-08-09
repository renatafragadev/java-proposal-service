package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.bo;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CountingVoteBO {

    private int eligibleVoters;
    private int total;
    private int totalYes;
    private int totalNo;
    private Session session;
}
