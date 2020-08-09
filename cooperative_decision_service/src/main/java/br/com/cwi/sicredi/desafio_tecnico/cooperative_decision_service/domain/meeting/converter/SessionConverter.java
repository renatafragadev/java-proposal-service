package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.SessionModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.VoteModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.SessionRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SessionConverter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    public static Session toEntity(SessionRequest sessionRequest) {
        Session session = new Session();
        session.setStartDateTime(sessionRequest.getStartDateTime());
        session.setEndDateTime(sessionRequest.getEndDateTime());
        session.setSchedule(ScheduleConverter.withId(sessionRequest.getScheduleId()));

        return session;
    }

    public static SessionModel toModel(Session session) {
        SessionModel sessionModel = new SessionModel();
        sessionModel.setId(session.getId());
        sessionModel.setStartDateTime(session.getStartDateTime().format(DATE_TIME_FORMATTER));
        sessionModel.setEndDateTime(session.getEndDateTime().format(DATE_TIME_FORMATTER));
        sessionModel.setVotes((Set<VoteModel>) VoteConverter.toListModel(session.getVotes()));

        return sessionModel;
    }

    public static Collection<SessionModel> toListModel(Collection<Session> sessionList) {
        List<SessionModel> sessionModelList = new ArrayList<>();

        sessionList.forEach(session -> {
            SessionModel sessionModel = new SessionModel();
            sessionModel.setId(session.getId());
            sessionModel.setStartDateTime(session.getStartDateTime().format(DATE_TIME_FORMATTER));
            sessionModel.setEndDateTime(session.getEndDateTime().format(DATE_TIME_FORMATTER));

            sessionModelList.add(sessionModel);
        });

        return sessionModelList;
    }


}
