package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoteService {

    Vote create(Vote vote);

    Vote findById(Long id);

    Page<Vote> find(Long associateId, Long sessionId, Pageable pageable);
}
