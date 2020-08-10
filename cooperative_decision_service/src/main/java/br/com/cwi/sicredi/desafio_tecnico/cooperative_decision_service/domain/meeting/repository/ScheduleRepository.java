package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {

    boolean existsByMeetingAndTitle(Meeting meeting, String title);

    Optional<Schedule> findByMeetingIdAndId(Long meetingId, Long id);

    Page<Schedule> findByMeetingId(Long meetingId, Pageable pageable);
}
