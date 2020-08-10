package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator.SessionStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccurateSessionModel {

    private long eligibleVoters;
    private long total;
    private long totalYes;
    private long totalNo;
    private SessionStatus status;
}
