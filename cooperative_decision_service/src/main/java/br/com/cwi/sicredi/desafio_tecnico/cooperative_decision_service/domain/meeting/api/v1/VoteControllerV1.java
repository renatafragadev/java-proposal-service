package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.v1;


import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.VoteModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.VoteRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter.VoteConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Vote;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.SessionService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.VoteService;
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
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteControllerV1 {

    private final SessionService sessionService;
    private final VoteService voteService;

    @PostMapping(path = "/sessions/{sessionId}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@Min(1) @PathVariable Long sessionId, @Valid @RequestBody VoteRequest
            voteRequest) {
        log.info("Controller - add | sessionId: {} | voteRequest: {}", sessionId, voteRequest);

        Session session = sessionService.findById(sessionId);
        Vote vote = voteService.create(VoteConverter.toEntity(voteRequest, session));

        log.info("Controller - add | status: {} | id: {}", HttpStatus.CREATED, vote.getId());
        return ResponseEntity.created(URIGenerator.get("/{id}", vote.getId())).build();
    }

    @GetMapping(path = "/votes/{id}")
    public ResponseEntity<Object> findById(@Min(1) @PathVariable Long id) {
        log.info("Controller - findById | id: {}", id);

        VoteModel voteModel = VoteConverter.toModel(voteService.findById(id));

        log.info("Controller - findById | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(voteModel));
    }

    @GetMapping(path = "/votes")
    public ResponseEntity<Object> find(@RequestParam Long associateId,
                                       @RequestParam(required = false) Long sessionId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        log.info("Controller - find | associateId: {} | sessionId: {}" + " page: {} | size: {}", associateId,
                sessionId, page, size);

        Page<Vote> votePage = voteService.find(associateId, sessionId, PageRequest.of(page, size));
        List<VoteModel> voteModelList = (List<VoteModel>) VoteConverter.toListModel(votePage.getContent());
        Page<VoteModel> modelPage = new PageImpl<>(voteModelList, votePage.getPageable(), votePage.getTotalElements());

        log.info("Controller - find | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(modelPage));
    }
}