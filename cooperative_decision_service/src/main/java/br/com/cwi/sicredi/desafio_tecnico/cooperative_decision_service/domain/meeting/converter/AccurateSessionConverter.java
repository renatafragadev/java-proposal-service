package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.AccurateSessionModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.bo.AccurateSessionBO;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator.SessionStatus;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.messaging.pojo.AccurateSessionMessage;

import java.time.LocalDateTime;
import java.util.Map;

public class AccurateSessionConverter {

    public static AccurateSessionModel toModel(AccurateSessionBO accurateSessionBO) {
        AccurateSessionModel accurateSessionModel = new AccurateSessionModel();
        accurateSessionModel.setEligibleVoters(accurateSessionBO.getEligibleVoters());
        accurateSessionModel.setTotal(accurateSessionBO.getTotal());
        accurateSessionModel.setTotalYes(accurateSessionBO.getTotalYes());
        accurateSessionModel.setTotalNo(accurateSessionBO.getTotalNo());
        accurateSessionModel.setStatus(getStatus(accurateSessionBO.getSession()));

        return accurateSessionModel;
    }

    public static SessionStatus getStatus(Session session) {
        LocalDateTime dateTimeWithoutSeconds = LocalDateTime.now().withSecond(0).withNano(0);

        return session.getEndDateTime().isBefore(dateTimeWithoutSeconds) ? SessionStatus.ENDED
                : SessionStatus.IN_PROGRESS;
    }

    public static AccurateSessionMessage toMessage(AccurateSessionBO accurateSessionBO) {
        AccurateSessionMessage accurateSessionMessage = new AccurateSessionMessage();
        accurateSessionMessage.setEligibleVoters(accurateSessionBO.getEligibleVoters());
        accurateSessionMessage.setTotal(accurateSessionBO.getTotal());
        accurateSessionMessage.setTotalYes(accurateSessionBO.getTotalYes());
        accurateSessionMessage.setTotalNo(accurateSessionBO.getTotalNo());
        accurateSessionMessage.setSession(Map.of("id", accurateSessionBO.getSession().getId(),
                "status", SessionStatus.ENDED));

        return accurateSessionMessage;
    }
}