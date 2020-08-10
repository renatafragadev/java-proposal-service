package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionModel {

    private Long id;
    private String startDateTime;
    private String endDateTime;
}
