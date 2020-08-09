package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.BaseUnitTest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Schedule;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository.MeetingRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository.ScheduleRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.impl.ScheduleServiceImpl;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.component.EntityValidator;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.ConflictException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.NoContentException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Unit Test: Schedule - Service Layer")
public class ScheduleServiceImplUT extends BaseUnitTest {

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private EntityValidator entityValidator;

    private Meeting meetingWithSchedules;
    private Meeting meetingWithManySchedules;
    private Meeting meetingWithoutSchedules;

    private Schedule schedule1;
    private Schedule schedule2;
    private Schedule schedule3;

    private Page<Schedule> schedulePage;

    @BeforeAll
    void init() {
        createMeetings();
        createSchedules();
        createSchedulePage();
    }

    @BeforeEach
    void initEach() {
        reset(meetingRepository, scheduleRepository, entityValidator);
    }

    @DisplayName("All tests of create method")
    @Nested
    class CreateMethodTest {

        @DisplayName("Should create schedule successfully")
        @Test
        void create_validSchedule_ReturnCreatedEntity() {
            Schedule schedule = new Schedule();
            schedule.setTitle(FAKER.beer().hop());
            schedule.setDescription(FAKER.beer().name());

            Meeting meetingWithScheduleAdded = meetingWithoutSchedules;
            meetingWithScheduleAdded.getSchedules().add(schedule);

            doNothing().when(entityValidator).isConflicting(anyBoolean(), anyString(), anyString());
            when(meetingRepository.save(any(Meeting.class))).thenReturn(meetingWithScheduleAdded);

           // Schedule scheduleResp = scheduleService.create(meetingWithoutSchedules, schedule);

          //  assertNotNull(scheduleResp);

            verify(entityValidator, times(1)).isConflicting(anyBoolean(), anyString(),
                    anyString());
            verify(meetingRepository, times(1)).save(any(Meeting.class));
        }

        @DisplayName("Should return error because schedule already exists")
        @Test
        void create_ExistingSchedule_ThrowConflictException() {
            doThrow(ConflictException.class).when(entityValidator).isConflicting(anyBoolean(), anyString(),
                    anyString());

           // assertThrows(ConflictException.class, () -> scheduleService.add(meetingWithSchedules, schedule1));

            verify(entityValidator, times(1)).isConflicting(anyBoolean(), anyString(),
                    anyString());
            verify(meetingRepository, times(0)).save(any(Meeting.class));
        }
    }

    @DisplayName("All tests of findById method")
    @Nested
    class FindByIdMethodTest {

        @DisplayName("Should consult schedule by id successfully")
        @Test
        void findById_ExistingSchedule_ReturnEntity() {
            when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule2));
            doNothing().when(entityValidator).isNonexistent(anyBoolean(), anyString());

            Schedule schedule = scheduleService.findById(schedule2.getId());

            assertNotNull(schedule);
        }

        @DisplayName("Should throw exception because there is no schedule")
        @Test
        void findById_NonexistentSchedule_ThrowNotFoundException() {
            when(scheduleRepository.findById(anyLong())).thenReturn(Optional.empty());
            doThrow(NotFoundException.class).when(entityValidator).isNonexistent(anyBoolean(), anyString());

            assertThrows(NotFoundException.class, () -> scheduleService.findById(4550L));

            verify(scheduleRepository, times(1)).findById(anyLong());
            verify(entityValidator, times(1)).isNonexistent(anyBoolean(), anyString());
        }
    }

    @DisplayName("All tests of findByMeetingIdAndId method")
    @Nested
    class FindByMeetingIdAndIdMethodTest {

        @DisplayName("Should consult schedule by meeting id and id successfully")
        @Test
        void findByMeetingIdAndId_ExistingSchedule_ReturnEntity() {
            when(scheduleRepository.findByMeetingIdAndId(anyLong(), anyLong())).thenReturn(Optional.of(schedule3));
            doNothing().when(entityValidator).isNonexistent(anyBoolean(), anyString());

            Schedule schedule = scheduleService.findByMeetingIdAndId(meetingWithSchedules.getId(), schedule3.getId());

            assertNotNull(schedule);
        }

        @DisplayName("Should throw exception because there is no schedule")
        @Test
        void findByMeetingIdAndId_NonexistentSchedule_ThrowNotFoundException() {
            when(scheduleRepository.findByMeetingIdAndId(anyLong(), anyLong())).thenReturn(Optional.empty());
            doThrow(NotFoundException.class).when(entityValidator).isNonexistent(anyBoolean(), anyString());

            assertThrows(NotFoundException.class, () -> scheduleService.findByMeetingIdAndId(meetingWithSchedules.getId(),
                    400L));

            verify(scheduleRepository, times(1)).findByMeetingIdAndId(anyLong(), anyLong());
            verify(entityValidator, times(1)).isNonexistent(anyBoolean(), anyString());
        }
    }

    @DisplayName("All tests of findAllByMeetingId method")
    @Nested
    class FindAllByMeetingIdMethodTest {

        @DisplayName("should return a paginated list")
        @Test
        void findAllByMeetingId_ExistingSchedules_ReturnFullPage() {
            when(scheduleRepository.findByMeetingId(anyLong(), any(Pageable.class))).thenReturn(schedulePage);
            doNothing().when(entityValidator).isEmpty(anyBoolean());

            Page<Schedule> page = scheduleService.findAllByMeetingId(meetingWithManySchedules.getId(),
                    PageRequest.of(0, 10));

            assertThat(page.getContent()).isNotEmpty();
            assertThat(page.getTotalElements()).isEqualTo(schedulePage.getTotalElements());
        }

        @DisplayName("Should throw exception because there are no registered schedules")
        @Test
        void findAllByMeetingId_NonexistentMeetings_ThrowNoContentException() {
            when(scheduleRepository.findByMeetingId(anyLong(), any(Pageable.class))).thenReturn(Page.empty());
            doThrow(NoContentException.class).when(entityValidator).isEmpty(anyBoolean());

            assertThrows(NoContentException.class, () -> scheduleService.findAllByMeetingId(meetingWithoutSchedules
                    .getId(), PageRequest.of(0, 10)));

            verify(scheduleRepository, times(1)).findByMeetingId(anyLong(),
                    any(Pageable.class));
            verify(entityValidator, times(1)).isEmpty(anyBoolean());
        }
    }

    private void createMeetings() {
        meetingWithSchedules = new Meeting();
        meetingWithSchedules.setId(1L);
        meetingWithSchedules.setTitle(FAKER.leagueOfLegends().rank());
        meetingWithSchedules.setDescription(FAKER.leagueOfLegends().masteries());
        meetingWithSchedules.setEventDate(LocalDateTime.now().plusMonths(1));

        meetingWithoutSchedules = new Meeting();
        meetingWithoutSchedules.setId(2L);
        meetingWithoutSchedules.setTitle(FAKER.app().author());
        meetingWithoutSchedules.setDescription(FAKER.app().name());
        meetingWithoutSchedules.setEventDate(LocalDateTime.now().plusDays(3));

        meetingWithManySchedules = new Meeting();
        meetingWithManySchedules.setId(3L);
        meetingWithManySchedules.setTitle(FAKER.app().author());
        meetingWithManySchedules.setDescription(FAKER.app().name());
        meetingWithManySchedules.setEventDate(LocalDateTime.now().plusYears(1));
    }

    private void createSchedules() {
        schedule1 = new Schedule();
        schedule1.setId(60L);
        schedule1.setTitle(FAKER.gameOfThrones().dragon());
        schedule1.setDescription(FAKER.gameOfThrones().city());

        schedule2 = new Schedule();
        schedule2.setId(61L);
        schedule2.setTitle(FAKER.country().name());
        schedule2.setDescription(FAKER.country().capital());

        schedule3 = new Schedule();
        schedule3.setId(62L);
        schedule3.setTitle(FAKER.backToTheFuture().character());
        schedule3.setDescription(FAKER.backToTheFuture().quote());

        meetingWithSchedules.getSchedules().addAll(Arrays.asList(schedule1, schedule2, schedule3));
    }

    private void createSchedulePage() {
        List<Schedule> scheduleList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Schedule schedule = new Schedule();
            schedule.setId((long) i);
            schedule.setTitle(FAKER.book().title());
            schedule.setDescription(FAKER.book().genre());

            scheduleList.add(schedule);
        }

        meetingWithManySchedules.getSchedules().addAll(scheduleList);
        schedulePage = new PageImpl<>(scheduleList, PageRequest.of(0, 10), scheduleList.size());
    }

}
