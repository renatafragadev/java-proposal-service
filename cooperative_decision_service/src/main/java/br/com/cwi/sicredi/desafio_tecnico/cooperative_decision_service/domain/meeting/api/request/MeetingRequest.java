package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.annotation.IsGreaterThanOrEqualNow;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MeetingRequest {

    @Size(max = 100)
    @NotNull
    private String title;

    @Size(max = 255)
    private String description;

    @IsGreaterThanOrEqualNow
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime eventDate;
}
