package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends AbstractException {

    private static final long serialVersionUID = -2516853146086236667L;

    private final String field;

    public ConflictException(String message, String resource, String field) {
        super(message, resource);
        this.field = field;
    }
}

