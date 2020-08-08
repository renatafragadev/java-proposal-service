package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.BaseUnitTest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository.SessionRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.impl.SessionServiceImpl;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.component.EntityValidator;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.BusinessException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.NoContentException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Unit Test: Session - Service Layer")
public class SessionServiceImplUT extends BaseUnitTest {

    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private EntityValidator entityValidator;

    private Meeting meeting1;
    private Meeting meeting2;

    private Schedule schedule1;
    private Schedule scheduleWithoutSession;
    private Schedule scheduleWithoutSession2;

    private Session sessionFromSchedule1;

    private Page<Session> sessionPage;

    @BeforeEach
    void initEach() {
        createMeetings();
        createSchedules();
        createSessions();
        createSessionPage();
        reset(sessionRepository, scheduleService, entityValidator);
    }

    @DisplayName("All tests of create method")
    @Nested
    class CreateMethodTest {

        @DisplayName("Should create session successfully")
        @Test
        void create_validSession_ReturnCreatedEntity() {
            Session session = new Session();
            session.setStartDateTime(meeting2.getEventDate().plusMinutes(5));
            session.setEndDateTime(session.getStartDateTime().plusMinutes(10));
            session.setSchedule(scheduleWithoutSession);

            when(scheduleService.findById(anyLong())).thenReturn(scheduleWithoutSession);
            when(sessionRepository.save(any(Session.class))).thenReturn(session);

            Session sessionResp = sessionService.create(session);

            assertNotNull(sessionResp);
        }

        @DisplayName("Should return an error because there is no informed schedule")
        @Test
        void create_NonexistentSchedule_ThrowBusinessException() {
            Schedule schedule = new Schedule();
            schedule.setId(3044L);

            Session session = new Session();
            session.setStartDateTime(meeting1.getEventDate().plusMinutes(5));
            session.setEndDateTime(session.getStartDateTime().plusMinutes(10));
            session.setSchedule(schedule);

            when(scheduleService.findById(anyLong())).thenThrow(NotFoundException.class);

            assertThrows(NotFoundException.class, () -> sessionService.create(session));

            verify(scheduleService, times(1)).findById(anyLong());
        }

        @DisplayName("Should return error because schedule already has session registered")
        @Test
        void create_ExistingSession_ThrowBusinessException() {
            Session session = new Session();
            session.setStartDateTime(meeting1.getEventDate().plusMinutes(5));
            session.setEndDateTime(session.getStartDateTime().plusMinutes(10));
            session.setSchedule(schedule1);

            when(scheduleService.findById(anyLong())).thenReturn(schedule1);

            assertThrows(BusinessException.class, () -> sessionService.create(session));

            verify(scheduleService, times(1)).findById(anyLong());
        }

        @DisplayName("Should create session and assign one minute increase to endDateTime")
        @Test
        void create_EndDateTimeIsNull_CreateEntityAndSetEndDatePlusOneMinute() {
            Session session = new Session();
            session.setStartDateTime(meeting2.getEventDate().plusMinutes(5));
            session.setSchedule(scheduleWithoutSession2);

            when(scheduleService.findById(anyLong())).thenReturn(scheduleWithoutSession2);
            when(sessionRepository.save(any(Session.class))).thenReturn(session);

            Session sessionResp = sessionService.create(session);

            assertNotNull(sessionResp);
            assertEquals(session.getStartDateTime().plusMinutes(1), sessionResp.getEndDateTime());
        }

        @DisplayName("Should return an error because the end date is less than the start date")
        @Test
        void create_EndDateTimeIsBeforeStart_ThrowBusinessException() {
            Session session = new Session();
            session.setStartDateTime(meeting2.getEventDate().plusMinutes(5));
            session.setEndDateTime(session.getStartDateTime().minusMinutes(3));
            session.setSchedule(scheduleWithoutSession);

            when(scheduleService.findById(anyLong())).thenReturn(scheduleWithoutSession);

            assertThrows(BusinessException.class, () -> sessionService.create(session));

            verify(scheduleService, times(1)).findById(anyLong());
        }

        @DisplayName("Should return an error because the startDateTime is less than the eventDate of the meeting")
        @Test
        void create_StartDateTimeIsBefore_ThrowBusinessException() {
            Session session = new Session();
            session.setStartDateTime(meeting2.getEventDate().minusMinutes(1));
            session.setEndDateTime(session.getStartDateTime().minusMinutes(3));
            session.setSchedule(scheduleWithoutSession);

            when(scheduleService.findById(anyLong())).thenReturn(scheduleWithoutSession);

            assertThrows(BusinessException.class, () -> sessionService.create(session));

            verify(scheduleService, times(1)).findById(anyLong());
        }
    }

    @DisplayName("All tests of findById method")
    @Nested
    class FindByIdMethodTest {

        @DisplayName("Should consult schedule by id successfully")
        @Test
        void findById_ExistingSchedule_ReturnEntity() {
            when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(sessionFromSchedule1));
            doNothing().when(entityValidator).isNonexistent(anyBoolean(), anyString());

            Session session = sessionService.findById(sessionFromSchedule1.getId());

            assertNotNull(session);
        }

        @DisplayName("Should throw exception because there is no session")
        @Test
        void findById_NonexistentSession_ThrowNotFoundException() {
            when(sessionRepository.findById(anyLong())).thenReturn(Optional.empty());
            doThrow(NotFoundException.class).when(entityValidator).isNonexistent(anyBoolean(), anyString());

            assertThrows(NotFoundException.class, () -> sessionService.findById(4550L));

            verify(sessionRepository, times(1)).findById(anyLong());
            verify(entityValidator, times(1)).isNonexistent(anyBoolean(), anyString());
        }
    }

    @DisplayName("All tests of findAll method")
    @Nested
    class FindAllMethodTest {

        @DisplayName("should return a paginated list")
        @Test
        void findAll_ExistingSchedules_ReturnFullPage() {
            when(sessionRepository.findAll(any(Pageable.class))).thenReturn(sessionPage);
            doNothing().when(entityValidator).isEmpty(anyBoolean());

            Page<Session> page = sessionService.findAll(PageRequest.of(0, 10));

            assertThat(page.getContent()).isNotEmpty();
            assertThat(page.getTotalElements()).isEqualTo(sessionPage.getTotalElements());
        }

        @DisplayName("Should throw exception because there are no registered schedules")
        @Test
        void findAllByMeetingId_NonexistentMeetings_ThrowNoContentException() {
            when(sessionRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
            doThrow(NoContentException.class).when(entityValidator).isEmpty(anyBoolean());

            assertThrows(NoContentException.class, () -> sessionService.findAll(PageRequest.of(0, 10)));

            verify(sessionRepository, times(1)).findAll(any(Pageable.class));
            verify(entityValidator, times(1)).isEmpty(anyBoolean());
        }
    }

    private void createMeetings() {
        meeting1 = new Meeting();
        meeting1.setId(1L);
        meeting1.setTitle(FAKER.leagueOfLegends().rank());
        meeting1.setDescription(FAKER.leagueOfLegends().masteries());
        meeting1.setEventDate(LocalDateTime.now().plusMonths(1));

        meeting2 = new Meeting();
        meeting2.setId(2L);
        meeting2.setTitle(FAKER.app().author());
        meeting2.setDescription(FAKER.app().name());
        meeting2.setEventDate(LocalDateTime.now().plusDays(3));
    }

    private void createSchedules() {
        schedule1 = new Schedule();
        schedule1.setId(60L);
        schedule1.setTitle(FAKER.gameOfThrones().dragon());
        schedule1.setDescription(FAKER.gameOfThrones().city());
        schedule1.setMeeting(meeting1);

        scheduleWithoutSession = new Schedule();
        scheduleWithoutSession.setId(62L);
        scheduleWithoutSession.setTitle(FAKER.country().name());
        scheduleWithoutSession.setDescription(FAKER.country().capital());
        scheduleWithoutSession.setMeeting(meeting2);

        scheduleWithoutSession2 = new Schedule();
        scheduleWithoutSession2.setId(61L);
        scheduleWithoutSession2.setTitle(FAKER.lordOfTheRings().character());
        scheduleWithoutSession2.setDescription(FAKER.lordOfTheRings().location());
        scheduleWithoutSession2.setMeeting(meeting2);
    }

    private void createSessions() {
        sessionFromSchedule1 = new Session();
        sessionFromSchedule1.setId(1L);
        sessionFromSchedule1.setStartDateTime(meeting1.getEventDate().plusMinutes(2));
        sessionFromSchedule1.setEndDateTime(sessionFromSchedule1.getStartDateTime().plusMinutes(30));

        schedule1.setSession(sessionFromSchedule1);
    }

    private void createSessionPage() {
        List<Session> sessionList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Session session = new Session();
            session.setStartDateTime(meeting1.getEventDate().plusMinutes(1));
            session.setEndDateTime(session.getStartDateTime().plusMinutes(4));

            sessionList.add(session);
        }

        sessionPage = new PageImpl<>(sessionList, PageRequest.of(0, 10), sessionList.size());
    }

}
