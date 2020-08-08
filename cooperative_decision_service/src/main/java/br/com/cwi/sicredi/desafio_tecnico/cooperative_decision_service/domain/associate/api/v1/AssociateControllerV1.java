package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.v1;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.request.AssociateRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.converter.AssociateConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.AssociateService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.converter.URIGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/associates", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssociateControllerV1 {

    private final AssociateService associateService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@Valid @RequestBody AssociateRequest associateRequest) {
        log.info("Controller - create | associateRequest: {}", associateRequest);

        Associate associate = associateService.create(AssociateConverter.toEntity(associateRequest));

        log.info("Controller - create | status: {} | id: {}", HttpStatus.CREATED, associate.getId());
        return ResponseEntity.created(URIGenerator.get("/{id}", associate.getId())).build();
    }
}
