package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.impl;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.bo.CountingVoteBO;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Vote;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository.VoteRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.AssociateService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.VoteService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.enumerator.DecisionType;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.SessionService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.component.EntityValidator;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator.I18nMessage;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.specification.VoteSpecification.byAssociateId;
import static br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.specification.VoteSpecification.bySessionId;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@RequiredArgsConstructor
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    private final AssociateService associateService;
    private final SessionService sessionService;

    private final EntityValidator entityValidator;

    @Override
    public Vote create(Vote vote) {
        log.info("Service - create | vote: {}", vote);

        if (voteRepository.existsByAssociateIdAndSessionId(vote.getAssociate().getId(), vote.getSession().getId())) {
            throw new BusinessException(I18nMessage.VOTE_ALREADY_EXITS.getKey(), Vote.class.getSimpleName());
        }
        validateSession(vote);
        validateAssociate(vote);

        return voteRepository.save(vote);
    }

    private void validateSession(Vote vote) {
        Session session = sessionService.findById(vote.getSession().getId());
        sessionService.validateIfCurrent(session);

        vote.setSession(session);
    }

    private void validateAssociate(Vote vote) {
        Associate associate = associateService.findById(vote.getAssociate().getId());

        associateService.validateIfEnabled(associate);
        associateService.validateIfInvitedMeeting(associate, vote.getSession().getSchedule().getMeeting());

        vote.setAssociate(associate);
    }

    @Override
    public Vote findById(Long id) {
        log.info("Service - findById | id: {}", id);

        Optional<Vote> voteOptional = voteRepository.findById(id);
        entityValidator.isNonexistent(voteOptional.isPresent(), Vote.class.getSimpleName());

        return voteOptional.get();
    }

    @Override
    public Page<Vote> findByAssociateId(Long associateId, Pageable pageable) {
        log.info("Service - findByAssociateId | associateId: {} | pageable: {}", associateId, pageable);

        Page<Vote> votePage = voteRepository.findAll(where(byAssociateId(associateId)), pageable);
        entityValidator.isEmpty(votePage.isEmpty());

        return votePage;
    }

    @Override
    public Page<Vote> findBySessionId(Long sessionId, Pageable pageable) {
        log.info("Service - findBySessionId | sessionId: {} | pageable: {}", sessionId, pageable);

        Page<Vote> votePage = voteRepository.findAll(where(bySessionId(sessionId)), pageable);
        entityValidator.isEmpty(votePage.isEmpty());

        return votePage;
    }

    @Scheduled(cron = "${cron.count-votes}")
    @Override
    public void countClosedSessionCron() {
        log.warn("Service - count closed session cron - start");

        LocalDateTime dateTimeWithoutSeconds = LocalDateTime.now().withSecond(0).withNano(0);

        sessionService.findByEndDateTime(dateTimeWithoutSeconds).forEach(session ->
                sessionService.forwardResult(session, countBySession(session)));

        log.warn("Service - count closed session cron - end");
    }

    @Override
    public CountingVoteBO countBySession(Session session) {
        log.info("Service - countBySession | session: {}", session);

        return CountingVoteBO.builder()
                .total(voteRepository.countBySession(session))
                .totalNo(voteRepository.countBySessionAndDecision(session, DecisionType.NO))
                .totalYes(voteRepository.countBySessionAndDecision(session, DecisionType.YES))
                .eligibleVoters(session.getSchedule().getMeeting().getAssociates().size())
                .session(session)
                .build();
    }
}