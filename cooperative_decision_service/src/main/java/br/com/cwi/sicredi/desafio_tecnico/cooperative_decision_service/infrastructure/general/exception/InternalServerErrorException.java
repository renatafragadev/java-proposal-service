package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends AbstractException {

    private static final long serialVersionUID = -7697398750230969825L;

    public InternalServerErrorException(String message, String resource) {
        super(message, resource);
    }
}
