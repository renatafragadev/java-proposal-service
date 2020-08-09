package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.v2;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.model.AssociateModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.converter.AssociateConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.AssociateService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.pojo.HttpSuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v2/associates", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssociateControllerV2 {

    private final AssociateService associateService;

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<Object> findByUuid(@PathVariable String uuid) {
        log.info("Controller - findByUuid | uuid: {}", uuid);

        AssociateModel associateModel = AssociateConverter.toModel(associateService.findByUuid(uuid));

        log.info("Controller - findByUuid | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(associateModel));
    }
}
