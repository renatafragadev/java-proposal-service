package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator;

public enum  I18nMessage {
    CONFLICT("error.resource.conflict"),
    NOT_FOUND("error.resource.not_found"),
    SERVICE_UNAVAILABLE("error.resource.service_unavailable"),
    SESSION_ALREADY_EXISTS("error.business.session.exists"),
    START_DATETIME_BEFORE_NOW("error.business.session.start_date_time.before_now"),
    START_DATETIME_BEFORE_MEETING("error.business.session.start_date_time.before_meeting"),
    END_DATETIME_BEFORE_START_DATETIME("error.business.session.end_date_time.before_start_date"),
    VOTE_ALREADY_EXITS("error.business.vote.exists"),
    SESSION_UNAVAILABLE("error.business.session.unavailable"),
    ASSOCIATE_DISABLED("error.business.associate.disabled"),
    ASSOCIATE_NOT_GUEST_MEETING("error.business.associate.not_guest_meeting");

    private final String key;

    I18nMessage(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
