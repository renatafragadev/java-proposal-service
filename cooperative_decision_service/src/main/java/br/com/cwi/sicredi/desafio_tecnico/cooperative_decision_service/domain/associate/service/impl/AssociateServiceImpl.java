package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.impl;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.repository.AssociateRepository;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.AssociateService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.meeting.entity.Meeting;
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
public class AssociateServiceImpl implements AssociateService {

    private final AssociateRepository associateRepository;

    private final EntityValidator entityValidator;

    @Override
    public Associate create(Associate associate) {
        log.info("Service - create | associate: {}", associate);

        entityValidator.isConflicting(associateRepository.existsByDocument(associate.getDocument()),
                Associate.class.getSimpleName(), "document");

        return associateRepository.save(associate);
    }

    @Override
    public Associate findById(Long id) {
        log.info("Service - findById | id: {}", id);

        Optional<Associate> associateOptional = associateRepository.findById(id);
        entityValidator.isNonexistent(associateOptional.isPresent(), Associate.class.getSimpleName());

        return associateOptional.get();
    }

    @Override
    public Page<Associate> findAll(Pageable pageable) {
        log.info("Service - findAll | pageable: {}", pageable);

        Page<Associate> associatePage = associateRepository.findAll(pageable);
        entityValidator.isEmpty(associatePage.isEmpty());

        return associatePage;
    }

    @Override
    public void validateIfEnabled(Associate associate) {
        log.info("Service - validateIfEnable | associate: {}", associate);

        if (!associate.getEnabled()) {
            throw new BusinessException(I18nMessage.ASSOCIATE_DISABLED.getKey(), Associate.class.getSimpleName());
        }
    }

    @Override
    public void validateIfInvitedMeeting(Associate associate, Meeting meeting) {
        log.info("Service - validateIfInvitedMeeting | associate: {} | meeting: {}", associate, meeting);

        if (associate.getMeetings().stream().noneMatch(m -> m.getMeeting().getId().equals(meeting.getId()))) {
            throw new BusinessException(I18nMessage.ASSOCIATE_NOT_GUEST_MEETING.getKey(), Associate.class
                    .getSimpleName());
        }
    }
}