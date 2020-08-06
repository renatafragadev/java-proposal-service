package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
public class MeetingHasAssociate implements Serializable {

    private static final long serialVersionUID = 6417165551201439760L;

    @EmbeddedId
    private MeetingHasAssociateId meetingHasAssociateId;

    @ManyToOne
    @MapsId("meeting_id")
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @MapsId("associate_id")
    @JoinColumn(name = "associate_id")
    private Associate associate;

    private Boolean moderator;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingHasAssociate that = (MeetingHasAssociate) o;
        return Objects.equals(meetingHasAssociateId, that.meetingHasAssociateId) &&
                Objects.equals(moderator, that.moderator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingHasAssociateId, moderator);
    }
}
