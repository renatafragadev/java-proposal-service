package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator;

public enum  I18nMessage {
    CONFLICT("{error.resource.not_found}"),
    NOT_FOUND("{error.resource.conflict}"),
    SERVICE_UNAVAILABLE("{error.resource.service_unavailable}");

    private final String key;

    I18nMessage(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
