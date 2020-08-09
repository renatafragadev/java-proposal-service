package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.bo.CountingVoteBO;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionService {

    Session create(Session session);

    Session findById(Long id);

    Page<Session> findAll(Pageable pageable);

    void validateIfCurrent(Session session);

    List<Session> findByEndDateTime(LocalDateTime endDateTime);

    void forwardResult(Session session, CountingVoteBO countingVoteBO);
}
