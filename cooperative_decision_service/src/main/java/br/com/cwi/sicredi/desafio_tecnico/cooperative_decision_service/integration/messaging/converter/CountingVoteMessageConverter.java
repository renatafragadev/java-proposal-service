package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.messaging.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.bo.CountingVoteBO;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator.SessionStatus;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.messaging.pojo.CountingVoteMessage;

import java.util.Map;

public class CountingVoteMessageConverter {

    public static CountingVoteMessage toMessage(CountingVoteBO countingVoteBO) {
        CountingVoteMessage countingVoteMessage = new CountingVoteMessage();
        countingVoteMessage.setEligibleVoters(countingVoteBO.getEligibleVoters());
        countingVoteMessage.setTotal(countingVoteBO.getTotal());
        countingVoteMessage.setTotalYes(countingVoteBO.getTotalYes());
        countingVoteMessage.setTotalNo(countingVoteBO.getTotalNo());
        countingVoteMessage.setSession(toSessionMap(countingVoteBO.getSession()));

        return countingVoteMessage;
    }

    private static Map<String, Object> toSessionMap(Session session) {
        return Map.of("id", session.getId(),
                "status", SessionStatus.ENDED);
    }
}
