package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.impl;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository.MeetingRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository.ScheduleRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.ScheduleService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.component.EntityValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final MeetingRepository meetingRepository;
    private final ScheduleRepository scheduleRepository;

    private final EntityValidator entityValidator;

    @Override
    public Schedule add(Meeting meeting, Schedule schedule) {
        log.info("Service - add | meeting: {} | schedule: {}", meeting, schedule);

        entityValidator.isConflicting(!meeting.getSchedules().add(schedule), Schedule.class.getSimpleName(),
                "title");

        meeting = meetingRepository.save(meeting);

        return meeting.getSchedules().stream().filter(s -> s.getTitle().equals(schedule.getTitle()))
                .findFirst().get();
    }

    @Override
    public Schedule findById(Long scheduleId) {
        log.info("Service - findById | scheduleId: {}", scheduleId);

        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
        entityValidator.isNonexistent(scheduleOptional.isPresent(), Schedule.class.getSimpleName());

        return scheduleOptional.get();
    }

    @Override
    public Page<Schedule> findAll(Pageable pageable) {
        log.info("Service - findAll | pageable: {}", pageable);

        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);
        entityValidator.isEmpty(schedulePage.isEmpty());

        return schedulePage;
    }
}
