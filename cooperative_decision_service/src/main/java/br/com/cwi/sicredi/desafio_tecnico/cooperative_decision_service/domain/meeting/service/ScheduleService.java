package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.bo.AccurateSessionBO;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    Schedule create(Schedule schedule);

    Schedule findById(Long scheduleId);

    Schedule findByMeetingIdAndId(Long meetingId, Long id);

    Page<Schedule> findAllByMeetingId(Long meetingId, Pageable pageable);

    void updateApproved(AccurateSessionBO accurateSessionBO);
}
