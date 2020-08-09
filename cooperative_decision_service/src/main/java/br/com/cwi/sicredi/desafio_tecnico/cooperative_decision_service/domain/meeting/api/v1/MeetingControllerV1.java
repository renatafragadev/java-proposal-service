package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.v1;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.MeetingModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.MeetingHasAssociateRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.MeetingRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter.MeetingConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.MeetingService;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/meetings", produces = MediaType.APPLICATION_JSON_VALUE)
public class MeetingControllerV1 {

    private final MeetingService meetingService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@Valid @RequestBody MeetingRequest meetingRequest) {
        log.info("Controller - create | meetingRequest: {}", meetingRequest);

        Meeting meeting = meetingService.create(MeetingConverter.toEntity(meetingRequest));

        log.info("Controller - create | status: {} | id: {}", HttpStatus.CREATED, meeting.getId());
        return ResponseEntity.created(URIGenerator.get("/{id}", meeting.getId())).build();
    }

    @PostMapping(path = "/{id}/associates", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Object> addAssociate(@PathVariable @Min(1) Long id, @Valid MeetingHasAssociateRequest
            meetingHasAssociateRequest) {
        log.info("Controller - addAssociate | id: {} | meetingHasAssociateRequest: {}", id, meetingHasAssociateRequest);

        Meeting meeting = meetingService.findById(id);
        meetingService.addAssociate(meeting, meetingHasAssociateRequest.getAssociateId(), meetingHasAssociateRequest
                .getModerator());

        log.info("Controller - create | status: {} | id: {}", HttpStatus.CREATED, meetingHasAssociateRequest
                .getAssociateId());
        return ResponseEntity.created(URIGenerator.get("/{associateId}", meetingHasAssociateRequest
                .getAssociateId())).build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> findById(@Min(1) @PathVariable Long id) {
        log.info("Controller - findById | id: {}", id);

        MeetingModel meetingModel = MeetingConverter.toModel(meetingService.findById(id));

        log.info("Controller - findById | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(meetingModel));
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        log.info("Controller - findAll | page: {} | size: {}", page, size);

        Page<Meeting> meetingPage = meetingService.findAll(PageRequest.of(page, size));
        Page<MeetingModel> modelPage = new PageImpl<>(MeetingConverter.toListModel(meetingPage.getContent()),
                meetingPage.getPageable(), meetingPage.getTotalElements());

        log.info("Controller - findAll | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(modelPage));
    }
}
