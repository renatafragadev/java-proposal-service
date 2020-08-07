package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException {

    private final String resource;

    public AbstractException(String message, String resource) {
        super(message);
        this.resource = resource;
    }
}
