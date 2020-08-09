package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.rabbit.constant;

public class RabbitConstant {

    public static final String SESSION_FANOUT_EXCHANGE = "session.fx";

    public static final String SESSION_COOPERATIVE_REPORT_QUEUE = "cooperative-report-service.session.notify";
    public static final String SESSION_HUMAN_RESOURCE_QUEUE = "human-resource-service.session.notify";
}
