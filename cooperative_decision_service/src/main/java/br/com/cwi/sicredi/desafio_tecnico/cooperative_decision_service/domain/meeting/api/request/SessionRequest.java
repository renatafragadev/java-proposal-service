package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.annotation.IsGreaterThanOrEqualNow;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SessionRequest {

    @IsGreaterThanOrEqualNow
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDateTime;

    @Min(1)
    @NotNull
    private Long scheduleId;
}
