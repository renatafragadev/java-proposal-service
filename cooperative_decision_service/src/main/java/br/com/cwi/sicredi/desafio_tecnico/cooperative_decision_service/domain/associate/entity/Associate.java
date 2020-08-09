package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.MeetingHasAssociate;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Vote;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Associate implements Serializable {

    private static final long serialVersionUID = -8631006634357189566L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String document;

    private String name;

    private LocalDate birthDate;

    private Boolean enabled;

    private String uuid;

    @OneToMany(mappedBy = "associate")
    private Set<Vote> votes = new HashSet<>();

    @OneToMany(mappedBy = "associate")
    private Set<MeetingHasAssociate> meetings = new HashSet<>();

    @Override
    public String toString() {
        return "Associate{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", enabled=" + enabled +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
