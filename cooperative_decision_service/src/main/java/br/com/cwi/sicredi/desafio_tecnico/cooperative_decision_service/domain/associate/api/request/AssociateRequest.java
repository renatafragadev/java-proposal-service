package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class AssociateRequest {

    @Size(min = 11, max = 11)
    private String document;

    @Size(max = 100)
    @NotBlank
    private String name;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull
    private Boolean enabled;
}
