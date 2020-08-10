package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.model;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Vote;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssociateModel {

    private Long id;
    private String uuid;
    private String document;
    private String name;
    private String birthDate;
    private Boolean enabled;
    private Set<Vote> votes = new HashSet<>();
    private Set<Meeting> meetings = new HashSet<>();
}
