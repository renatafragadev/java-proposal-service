package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.impl;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.AssociateService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Vote;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.repository.VoteRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.SessionService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.VoteService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.component.EntityValidator;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator.I18nMessage;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
        log.info("Service - create |  vote: {}", vote);

        sessionService.validateIfCurrent(vote.getSession());

        if (voteRepository.existsByAssociateIdAndSession(vote.getAssociate().getId(), vote.getSession())) {
            throw new BusinessException(I18nMessage.VOTE_ALREADY_EXITS.getKey(), Vote.class.getSimpleName());
        }

        validateAssociate(vote);

        return voteRepository.save(vote);
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
    public Page<Vote> find(Long associateId, Long sessionId, Pageable pageable) {
        log.info("Service - findByAssociateId | associateId: {} | sessionId: {} | pageable: {}", associateId,
                sessionId, pageable);

        Page<Vote> votePage;

        if (Objects.nonNull(associateId) && Objects.nonNull(sessionId)) {
            votePage = voteRepository.findAll(where(byAssociateId(associateId).and(bySessionId(sessionId))),
                    pageable);
        } else {
            votePage = voteRepository.findAll(where(byAssociateId(associateId)), pageable);
        }

        entityValidator.isEmpty(votePage.isEmpty());
        return votePage;
    }
}
