package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssociateService {

    Associate create(Associate associate);

    Associate findById(Long id);

    Page<Associate> findAll(Pageable pageable);
}
