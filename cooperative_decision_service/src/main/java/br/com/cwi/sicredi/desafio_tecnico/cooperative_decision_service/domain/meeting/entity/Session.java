package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Vote;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Session implements Serializable {

    private static final long serialVersionUID = -5609623016293783953L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @OneToOne
    private Schedule schedule;

    @OneToMany(mappedBy = "session")
    private Set<Vote> votes = new HashSet<>();
}
