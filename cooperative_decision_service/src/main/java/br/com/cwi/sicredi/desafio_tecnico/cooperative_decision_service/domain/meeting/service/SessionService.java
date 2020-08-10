package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.bo.AccurateSessionBO;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SessionService {

    Session create(Session session);

    Session findById(Long id);

    Page<Session> findAll(Pageable pageable);

    void validateIfCurrent(Session session);

    void closedSessionCron();

    AccurateSessionBO findAccurateSession(Session session);
}
