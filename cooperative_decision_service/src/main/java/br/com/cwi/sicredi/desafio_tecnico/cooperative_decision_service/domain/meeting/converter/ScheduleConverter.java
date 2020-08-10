package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.ScheduleModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.ScheduleRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;

import java.util.List;
import java.util.stream.Collectors;

public class ScheduleConverter {

    public static Schedule toEntity(ScheduleRequest scheduleRequest, Meeting meeting) {
        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleRequest.getTitle());
        schedule.setDescription(scheduleRequest.getDescription());
        schedule.setMeeting(meeting);

        return schedule;
    }

    public static Schedule withId(Long scheduleId) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);

        return schedule;
    }

    public static ScheduleModel toModel(Schedule schedule) {
        ScheduleModel scheduleModel = new ScheduleModel();
        scheduleModel.setId(schedule.getId());
        scheduleModel.setTitle(schedule.getTitle());
        scheduleModel.setDescription(schedule.getDescription());
        scheduleModel.setApproved(schedule.getApproved());

        return scheduleModel;
    }
    public static List<ScheduleModel> toListModel(List<Schedule> scheduleList) {
        return scheduleList.stream().map(ScheduleConverter::toModel).collect(Collectors.toList());
    }
}
