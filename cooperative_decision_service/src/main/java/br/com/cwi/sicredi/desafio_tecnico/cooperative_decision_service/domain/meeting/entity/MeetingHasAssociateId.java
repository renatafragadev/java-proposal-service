package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MeetingHasAssociateId implements Serializable {

    private static final long serialVersionUID = -3009612627751806251L;

    @Column(name = "meeting_id")
    private Long meetingId;

    @Column(name = "associate_id")
    private Long associateId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingHasAssociateId that = (MeetingHasAssociateId) o;
        return Objects.equals(meetingId, that.meetingId) &&
                Objects.equals(associateId, that.associateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, associateId);
    }
}
