package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.enumerator.DocumentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Associate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String document;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String name;

    private LocalDate birthDate;

    private Boolean enabled;

    @OneToMany(mappedBy = "associate")
    private Set<Vote> votes = new HashSet<>();
}
