package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.model.AssociateModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.enumerator.DecisionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteModel {

    private Long id;
    private AssociateModel associate;
    private DecisionType decision;
    private String seem;
}
