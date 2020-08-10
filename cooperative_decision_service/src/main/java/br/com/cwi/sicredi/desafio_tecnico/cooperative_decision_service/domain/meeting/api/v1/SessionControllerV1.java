package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.v1;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.AccurateSessionModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.SessionModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.SessionRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.bo.AccurateSessionBO;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter.AccurateSessionConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter.SessionConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.SessionService;
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
@RequestMapping(path = "/v1/sessions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionControllerV1 {

    private final SessionService sessionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@Valid @RequestBody SessionRequest sessionRequest) {
        log.info("Controller - create | sessionRequest: {}", sessionRequest);

        Session session = sessionService.create(SessionConverter.toEntity(sessionRequest));

        log.info("Controller - create | status: {} | id: {}", HttpStatus.CREATED, session.getId());
        return ResponseEntity.created(URIGenerator.get("/{id}", session.getId())).build();
    }

    @GetMapping(path = "/{id}/accurate")
    public ResponseEntity<Object> findAccurate(@PathVariable Long id) {
        log.info("Controller - findAccurate | id: {}", id);

        AccurateSessionBO accurateSessionBO = sessionService.findAccurateSession(sessionService.findById(id));
        AccurateSessionModel accurateSessionModel = AccurateSessionConverter.toModel(accurateSessionBO);

        log.info("Controller - findAccurate | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(accurateSessionModel);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> findById(@Min(1) @PathVariable Long id) {
        log.info("Controller - findById | id: {}", id);

        SessionModel sessionModel = SessionConverter.toModel(sessionService.findById(id));

        log.info("Controller - findById | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(sessionModel));
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        log.info("Controller - findAll | page: {} | size: {}", page, size);

        Page<Session> sessionPage = sessionService.findAll(PageRequest.of(page, size));
        List<SessionModel> sessionList = (List<SessionModel>) SessionConverter.toListModel(sessionPage.getContent());
        Page<SessionModel> modelPage = new PageImpl<>(sessionList, sessionPage.getPageable(), sessionPage
                .getTotalElements());

        log.info("Controller - findAll | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(modelPage));
    }
}
