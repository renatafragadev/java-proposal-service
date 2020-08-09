package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.bo;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class AccurateSessionBO {

    private Session session;
    private long eligibleVoters;
    private long total;
    private long totalYes;
    private long totalNo;
}
