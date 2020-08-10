package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.user_info.pojo;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.user_info.enumerator.VoteStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VoteStatusResponse {

    private VoteStatus status;
}
