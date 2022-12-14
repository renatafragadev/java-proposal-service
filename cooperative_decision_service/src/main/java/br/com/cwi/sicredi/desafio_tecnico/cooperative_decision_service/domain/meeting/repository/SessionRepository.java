package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends PagingAndSortingRepository<Session, Long> {

    List<Session> findByEndDateTime(LocalDateTime localDateTime);
}
