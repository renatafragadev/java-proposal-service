package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.pojo;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.enumerator.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
@Builder
public class HttpErrorResponseDetail implements Serializable {

    private static final long serialVersionUID = -2376015111576039874L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String resource;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;

    private String message;

    private ErrorCode code;
}
