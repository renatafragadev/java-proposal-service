package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Schedule implements Serializable {

    private static final long serialVersionUID = -6658754642780392788L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    private Meeting meeting;

    @OneToOne(mappedBy = "schedule")
    private Session session;

    private Boolean approved;

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", approved=" + approved +
                '}';
    }
}
