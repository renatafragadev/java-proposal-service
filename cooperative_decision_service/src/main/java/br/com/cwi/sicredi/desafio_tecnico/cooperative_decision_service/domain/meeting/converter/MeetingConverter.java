package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.api.request.MeetingRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;

public class MeetingConverter {

    public static Meeting toEntity(MeetingRequest meetingRequest) {
        Meeting meeting = new Meeting();
        meeting.setTitle(meetingRequest.getTitle());
        meeting.setDescription(meetingRequest.getDescription());
        meeting.setEventDate(meetingRequest.getEventDate());

        return meeting;
    }
}
