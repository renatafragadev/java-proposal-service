package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.enumerator;


public enum ErrorCode {
    ALREADY_EXISTS("already_exists"),
    MISSING("missing"),
    MISSING_FIELD("missing_field"),
    MISSING_PARAM("missing_param"),
    INVALID("invalid"),
    THIRD_SERVICE_ERROR("third_services_error");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
