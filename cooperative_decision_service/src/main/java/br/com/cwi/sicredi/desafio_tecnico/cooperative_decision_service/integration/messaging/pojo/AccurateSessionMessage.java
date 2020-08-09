package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.messaging.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccurateSessionMessage {

    private long eligibleVoters;
    private long total;
    private long totalYes;
    private long totalNo;
    private Map<String, Object> session;

}
