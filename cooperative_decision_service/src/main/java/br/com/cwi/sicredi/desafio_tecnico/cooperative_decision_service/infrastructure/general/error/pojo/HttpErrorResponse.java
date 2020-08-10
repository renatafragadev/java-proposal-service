package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ToString
@Getter
@Setter
public class HttpErrorResponse implements Serializable {

    private static final long serialVersionUID = 103437433639071232L;

    private final long timestamp;
    private final String date;
    private final String status;
    private final List<HttpErrorResponseDetail> details;

    public HttpErrorResponse(List<HttpErrorResponseDetail> details) {
        this.timestamp = System.currentTimeMillis();
        this.date =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        this.status = "error";
        this.details = details;
    }
}
