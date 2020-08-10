package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.v1;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.ScheduleModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.ScheduleRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter.ScheduleConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.MeetingService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.ScheduleService;
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
@RequestMapping(path = "/v1/meetings/{meetingId}/schedules", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleControllerV1 {

    private final MeetingService meetingService;
    private final ScheduleService scheduleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@PathVariable Long meetingId, @Valid @RequestBody ScheduleRequest
            scheduleRequest) {
        log.info("Controller - add | meetingId: {} | scheduleRequest: {}", meetingId, scheduleRequest);

        Meeting meeting = meetingService.findById(meetingId);
        Schedule schedule = scheduleService.create(ScheduleConverter.toEntity(scheduleRequest, meeting));

        log.info("Controller - add | status: {} | id: {}", HttpStatus.CREATED, schedule.getId());
        return ResponseEntity.created(URIGenerator.get("/{id}", schedule.getId())).build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> findByMeetingIdAndId(@Min(1) @PathVariable Long meetingId,
                                                       @Min(1) @PathVariable Long id) {
        log.info("Controller - findById | meetingId: {} | id: {}", meetingId, id);

        ScheduleModel scheduleModel = ScheduleConverter.toModel(scheduleService.findByMeetingIdAndId(meetingId, id));

        log.info("Controller - findById | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(scheduleModel));
    }

    @GetMapping
    public ResponseEntity<Object> findAllByMeetingId(@Min(1) @PathVariable Long meetingId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        log.info("Controller - findAll | meetingId: {} | page: {} | size: {}", meetingId, page, size);

        Page<Schedule> schedulePage = scheduleService.findAllByMeetingId(meetingId, PageRequest.of(page, size));
        Page<ScheduleModel> modelPage = new PageImpl<>(ScheduleConverter.toListModel(schedulePage.getContent()),
                schedulePage.getPageable(), schedulePage.getTotalElements());

        log.info("Controller - findAll | status: {}", HttpStatus.OK);
        return ResponseEntity.ok(new HttpSuccessResponse(modelPage));
    }
}
