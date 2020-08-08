package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.impl;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository.SessionRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.ScheduleService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.SessionService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.component.EntityValidator;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator.I18nMessage;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private final ScheduleService scheduleService;

    private final EntityValidator entityValidator;

    @Override
    public Session create(Session session) {
        log.info("Service - create | session: {}", session);

        Schedule schedule = scheduleService.findById(session.getSchedule().getId());

        if (Objects.nonNull(schedule.getSession())) {
            throw new BusinessException(I18nMessage.SESSION_ALREADY_EXISTS.getKey(), Session.class.getSimpleName());
        }

        session.setSchedule(schedule);
        validateDates(session);

        return sessionRepository.save(session);
    }

    private void validateDates(Session session) {
        if (Objects.isNull(session.getEndDateTime())) {
            session.setEndDateTime(session.getStartDateTime().plusMinutes(1));
        } else {
            if(session.getEndDateTime().isBefore(session.getStartDateTime())) {
                throw new BusinessException(I18nMessage.END_DATETIME_BEFORE_START_DATETIME.getKey(),
                        Session.class.getSimpleName());
            }
        }
        if(session.getStartDateTime().isBefore(session.getSchedule().getMeeting().getEventDate())) {
            throw new BusinessException(I18nMessage.START_DATETIME_BEFORE_MEETING.getKey(), Session.class.getSimpleName());
        }
    }

    @Override
    public Session findById(Long id) {
        log.info("Service - findById | id: {}", id);

        Optional<Session> sessionOptional = sessionRepository.findById(id);
        entityValidator.isNonexistent(sessionOptional.isPresent(), Session.class.getSimpleName());

        return sessionOptional.get();
    }

    @Override
    public Page<Session> findAll(Pageable pageable) {
        log.info("Service - findAll | pageable: {}", pageable);

        Page<Session> sessionPage = sessionRepository.findAll(pageable);
        entityValidator.isEmpty(sessionPage.isEmpty());

        return sessionPage;
    }

    @Override
    public void validateIfCurrent(Session session) {
        log.info("Service - validateIfCurrent | session: {}", session);

        LocalDateTime dateTimeWithoutSeconds = LocalDateTime.now().withSecond(0).withNano(0);

        if (session.getStartDateTime().isAfter(dateTimeWithoutSeconds) ||
                session.getEndDateTime().isBefore(dateTimeWithoutSeconds)) {
            throw new BusinessException(I18nMessage.SESSION_UNAVAILABLE.getKey(), Session.class.getSimpleName());
        }
    }
}
