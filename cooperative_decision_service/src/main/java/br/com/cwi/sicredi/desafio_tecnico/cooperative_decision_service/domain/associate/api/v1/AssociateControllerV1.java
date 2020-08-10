package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.v1;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.model.AssociateModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.request.AssociateRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.converter.AssociateConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.AssociateService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.converter.URIGenerator;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.pojo.HttpSuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

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

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> findById(@Min(1) @PathVariable Long id) {
        log.info("Controller - findById | id: {}", id);

        AssociateModel associateModel = AssociateConverter.toModel(associateService.findById(id));

        log.info("Controller - findById | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(associateModel));
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        log.info("Controller - findAll | page: {} | size: {}", page, size);

        Page<Associate> associatePage = associateService.findAll(PageRequest.of(page, size));
        List<AssociateModel> modelList = (List<AssociateModel>) AssociateConverter.toListModel(associatePage.getContent());
        Page<AssociateModel> modelPage = new PageImpl<>(modelList, associatePage.getPageable(), associatePage
                .getTotalElements());

        log.info("Controller - findAll | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(modelPage));
    }
}
