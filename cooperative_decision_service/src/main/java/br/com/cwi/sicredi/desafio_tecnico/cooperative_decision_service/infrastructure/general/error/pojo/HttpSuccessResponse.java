package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.pojo;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
public class HttpSuccessResponse implements Serializable {

    private static final long serialVersionUID = -4958610313897546027L;

    private final String status;
    private final Object object;

    public HttpSuccessResponse(Object object) {
        this.status = "success";
        this.object = object;
    }
}
