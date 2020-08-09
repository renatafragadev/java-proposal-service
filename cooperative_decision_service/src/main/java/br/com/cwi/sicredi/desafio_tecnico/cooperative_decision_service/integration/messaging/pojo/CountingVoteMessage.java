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
public class CountingVoteMessage {

    private int eligibleVoters;
    private int total;
    private int totalYes;
    private int totalNo;
    private Map<String, Object> session;

}
