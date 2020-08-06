package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {
}