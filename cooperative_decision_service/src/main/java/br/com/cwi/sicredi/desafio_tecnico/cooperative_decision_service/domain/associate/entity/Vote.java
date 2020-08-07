package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.enumerator.DecisionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Vote implements Serializable {

    private static final long serialVersionUID = -173611604079561040L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Session session;

    @ManyToOne
    private Associate associate;

    @Enumerated(EnumType.STRING)
    private DecisionType decision;

    private String seem;


}
