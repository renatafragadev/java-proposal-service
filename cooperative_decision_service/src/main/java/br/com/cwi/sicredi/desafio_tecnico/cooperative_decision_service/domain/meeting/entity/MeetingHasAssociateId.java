package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class MeetingHasAssociateId implements Serializable {

    private static final long serialVersionUID = -3009612627751806251L;

    @Column(name = "associate_id")
    private Long associateId;

    @Column(name = "meeting_id")
    private Long meetingId;
}
