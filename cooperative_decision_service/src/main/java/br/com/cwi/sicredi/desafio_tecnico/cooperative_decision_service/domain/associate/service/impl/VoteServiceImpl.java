package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.impl;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Vote;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.repository.VoteRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.AssociateService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.VoteService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Session;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.service.SessionService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.component.EntityValidator;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator.I18nMessage;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        Session session = sessionService.findById(vote.getSession().getId());
        sessionService.validateIfCurrentSession(session);

        vote.setSession(session);
        vote.setAssociate(associateService.findById(vote.getAssociate().getId()));

        return voteRepository.save(vote);
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
        log.info("Service - find | associateId: {} | sessionId: {} | pageable: {}", associateId, sessionId, pageable);



        return null;
    }


}
