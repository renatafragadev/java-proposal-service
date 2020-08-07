package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends AbstractException {

    private static final long serialVersionUID = -9147014777463057292L;

    public BusinessException(String message, String resource) {
        super(message, resource);
    }
}
