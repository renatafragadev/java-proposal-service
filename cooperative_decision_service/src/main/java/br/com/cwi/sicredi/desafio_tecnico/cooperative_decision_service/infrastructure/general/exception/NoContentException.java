package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {

    private static final long serialVersionUID = -3886090096833528187L;
}
