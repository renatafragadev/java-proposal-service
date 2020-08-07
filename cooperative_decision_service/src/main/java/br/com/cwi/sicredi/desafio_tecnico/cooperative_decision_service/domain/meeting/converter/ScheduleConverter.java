package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.v1.request.ScheduleRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;

public class ScheduleConverter {

    public static Schedule toEntity(ScheduleRequest scheduleRequest) {
        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleRequest.getTitle());
        schedule.setDescription(scheduleRequest.getDescription());

        return schedule;
    }

    public static Schedule toEntityId(Long scheduleId) {
       Schedule schedule = new Schedule();
       schedule.setId(scheduleId);

       return schedule;
    }
}
