package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.model.MeetingModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.MeetingRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MeetingConverter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    public static Meeting toEntity(MeetingRequest meetingRequest) {
        Meeting meeting = new Meeting();
        meeting.setTitle(meetingRequest.getTitle());
        meeting.setDescription(meetingRequest.getDescription());
        meeting.setEventDate(meetingRequest.getEventDate());

        return meeting;
    }

    public static MeetingModel toModel(Meeting meeting) {
        MeetingModel meetingModel = new MeetingModel();
        meetingModel.setId(meeting.getId());
        meetingModel.setTitle(meeting.getTitle());
        meetingModel.setDescription(meeting.getDescription());
        meetingModel.setEventDate(meeting.getEventDate().format(DATE_TIME_FORMATTER));

        return meetingModel;
    }

    public static List<MeetingModel> toListModel(List<Meeting> meetingList) {
        return meetingList.stream().map(MeetingConverter::toModel).collect(Collectors.toList());
    }
}
