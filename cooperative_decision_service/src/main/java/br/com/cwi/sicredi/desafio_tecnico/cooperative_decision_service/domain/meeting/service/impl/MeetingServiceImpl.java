package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.impl;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository.MeetingRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.MeetingService;
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
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;

    private final EntityValidator entityValidator;

    @Override
    public Meeting create(Meeting meeting) {
        log.info("Service - create | meeting: {}", meeting);

        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting findById(Long id) {
        log.info("Service - findById | id: {}", id);

        Optional<Meeting> meetingOptional = meetingRepository.findById(id);
        entityValidator.isNonexistent(meetingOptional.isPresent(), Meeting.class.getSimpleName());

        return meetingOptional.get();
    }

    @Override
    public Page<Meeting> findAll(Pageable pageable) {
        log.info("Service - findAll | pageable: {}", pageable);

        Page<Meeting> meetingPage = meetingRepository.findAll(pageable);
        entityValidator.isEmpty(meetingPage.isEmpty());

        return meetingPage;
    }
}
