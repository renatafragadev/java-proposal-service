package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class MeetingHasAssociateRequest {

    @Min(1)
    @NotNull
    private Long associateId;

    @NotNull
    private Boolean moderator;
}
