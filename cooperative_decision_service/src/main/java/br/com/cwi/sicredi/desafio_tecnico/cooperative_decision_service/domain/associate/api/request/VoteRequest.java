package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.request;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.enumerator.DecisionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class VoteRequest {

    @NotNull
    private DecisionType decision;

    @Size(max = 255)
    private String seem;

    @Min(1)
    @NotNull
    private Long associateId;

    @Min(1)
    @NotNull
    private Long sessionId;
}
