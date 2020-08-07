package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.v1;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.v1.request.ScheduleRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter.ScheduleConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.MeetingService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.ScheduleService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.converter.URIGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/meetings/{meetingId}/schedules", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleController {

    private final MeetingService meetingService;
    private final ScheduleService scheduleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@PathVariable Long meetingId, @Valid @RequestBody ScheduleRequest
            scheduleRequest) {
        log.info("Controller - add | meetingId: {} | scheduleRequest: {}", meetingId, scheduleRequest);

        Meeting meeting = meetingService.findById(meetingId);
        Schedule schedule = scheduleService.add(meeting, ScheduleConverter.toEntity(scheduleRequest));

        log.info("Controller - add | status: {} | id: {}", HttpStatus.CREATED, schedule.getId());
        return ResponseEntity.created(URIGenerator.get("/{id}", schedule.getId())).build();
    }
}
