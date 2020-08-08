package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.BaseUnitTest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository.MeetingRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.impl.MeetingServiceImpl;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
@DisplayName("Unit Test: Meeting - Service Layer")
public class MeetingServiceImplUT extends BaseUnitTest {

    @InjectMocks
    private MeetingServiceImpl meetingService;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private EntityValidator entityValidator;

    private Meeting meetingAlreadyExists1;
    private Meeting meetingAlreadyExists2;

    private Page<Meeting> meetingPage;

    @BeforeAll
    void init() {
        createMeetings();
        createMeetingPages();
    }

    @BeforeEach
    void initEach() {
        reset(meetingRepository, entityValidator);
    }

    @DisplayName("All tests of create method")
    @Nested
    class CreateMethodTest {

        @DisplayName("Should create meeting successfully")
        @Test
        void create_validMeeting_ReturnCreatedEntity() {
            Meeting meeting = new Meeting();
            meeting.setTitle(FAKER.leagueOfLegends().rank());
            meeting.setDescription(FAKER.leagueOfLegends().masteries());
            meeting.setEventDate(LocalDateTime.now());

            when(meetingRepository.existsByTitleAndEventDateBetween(anyString(), any(LocalDateTime.class),
                    any(LocalDateTime.class))).thenReturn(false);
            when(meetingRepository.save(meeting)).thenReturn(meeting);

            Meeting meetingResp = meetingService.create(meeting);

            assertNotNull(meetingResp);

            verify(entityValidator, times(0)).isConflicting(anyBoolean(), anyString(),
                    anyString());
        }

        @DisplayName("Should throw exception because there is already a meeting with the same title and date")
        @Test
        void create_ExistingTitleAndSameDate_ThrowConflictException() {
            Meeting meeting = new Meeting();
            meeting.setTitle(meetingAlreadyExists1.getTitle());
            meeting.setDescription(FAKER.leagueOfLegends().masteries());
            meeting.setEventDate(meetingAlreadyExists1.getEventDate());

            when(meetingRepository.existsByTitleAndEventDateBetween(anyString(), any(LocalDateTime.class),
                    any(LocalDateTime.class))).thenReturn(true);
            doThrow(ConflictException.class).when(entityValidator).isConflicting(anyBoolean(), anyString(),
                    anyString());

            assertThrows(ConflictException.class, () -> meetingService.create(meeting));

            verify(meetingRepository, times(1)).existsByTitleAndEventDateBetween(anyString(),
                    any(LocalDateTime.class), any(LocalDateTime.class));
            verify(entityValidator, times(1)).isConflicting(anyBoolean(), anyString(),
                    anyString());
            verify(meetingRepository, times(0)).save(any(Meeting.class));
        }
    }

    @DisplayName("All tests of findById method")
    @Nested
    class FindByIdMethodTest {

        @DisplayName("Should consult meeting by id successfully")
        @Test
        void findById_ExistingMeeting_ReturnEntity() {
            when(meetingRepository.findById(meetingAlreadyExists2.getId())).thenReturn(Optional.of(
                    meetingAlreadyExists2));
            doNothing().when(entityValidator).isNonexistent(anyBoolean(), anyString());

            Meeting meeting = meetingService.findById(meetingAlreadyExists2.getId());

            assertNotNull(meeting);
        }

        @DisplayName("Should throw exception because there is no meeting")
        @Test
        void findById_NonexistentMeeting_ThrowNotFoundException() {
            when(meetingRepository.findById(anyLong())).thenReturn(Optional.empty());
            doThrow(NotFoundException.class).when(entityValidator).isNonexistent(anyBoolean(), anyString());

            assertThrows(NotFoundException.class, () -> meetingService.findById(455L));

            verify(meetingRepository, times(1)).findById(anyLong());
            verify(entityValidator, times(1)).isNonexistent(anyBoolean(), anyString());
        }
    }

    @DisplayName("All tests of findAll method")
    @Nested
    class FindAllMethodTest {

        @DisplayName("should return a paginated list")
        @Test
        void findAll_ExistingMeetings_ReturnFullPage() {
            when(meetingRepository.findAll(any(Pageable.class))).thenReturn(meetingPage);
            doNothing().when(entityValidator).isEmpty(anyBoolean());

            Page<Meeting> page = meetingService.findAll(PageRequest.of(0,10));

            assertThat(page.getContent()).isNotEmpty();
            assertThat(page.getTotalElements()).isEqualTo(meetingPage.getTotalElements());
        }

        @DisplayName("Should throw exception because there are no registered meetings")
        @Test
        void findAll_NonexistentMeetings_ThrowNoContentException() {
            when(meetingRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
            doThrow(NoContentException.class).when(entityValidator).isEmpty(anyBoolean());

            assertThrows(NoContentException.class, () -> meetingService.findAll(PageRequest.of(0,10)));

            verify(meetingRepository, times(1)).findAll(any(Pageable.class));
            verify(entityValidator, times(1)).isEmpty(anyBoolean());
        }
    }

    private void createMeetings() {
        meetingAlreadyExists1 = new Meeting();
        meetingAlreadyExists1.setId(1L);
        meetingAlreadyExists1.setTitle(FAKER.leagueOfLegends().rank());
        meetingAlreadyExists1.setDescription(FAKER.leagueOfLegends().masteries());
        meetingAlreadyExists1.setEventDate(LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 30)));

        meetingAlreadyExists2 = new Meeting();
        meetingAlreadyExists2.setId(1L);
        meetingAlreadyExists2.setTitle(FAKER.app().author());
        meetingAlreadyExists2.setDescription(FAKER.app().name());
        meetingAlreadyExists2.setEventDate(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
    }

    private void createMeetingPages() {
        List<Meeting> meetingList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Meeting meeting = new Meeting();
            meeting.setId(FAKER.random().nextLong());
            meeting.setTitle(FAKER.leagueOfLegends().rank());
            meeting.setDescription(FAKER.leagueOfLegends().masteries());
            meeting.setEventDate(LocalDateTime.now());

            meetingList.add(meeting);
        }

        meetingPage = new PageImpl<>(meetingList, PageRequest.of(0, 10), meetingList.size());
    }
}
