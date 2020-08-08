package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.SessionRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;

public class SessionConverter {

    public static Session toEntity(SessionRequest sessionRequest) {
        Session session = new Session();
        session.setStartDateTime(sessionRequest.getStartDateTime());
        session.setEndDateTime(sessionRequest.getEndDateTime());
        session.setSchedule(ScheduleConverter.withId(sessionRequest.getScheduleId()));

        return session;
    }

    public static Session withId(Long sessionId) {
        Session session = new Session();
        session.setId(sessionId);

        return session;
    }
}
