package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.request.AssociateRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;

public class AssociateConverter {

    public static Associate toEntity(AssociateRequest associateRequest) {
        Associate associate = new Associate();
        associate.setDocument(associateRequest.getDocument());
        associate.setName(associateRequest.getName());
        associate.setBirthDate(associateRequest.getBirthDate());
        associate.setEnabled(associateRequest.getEnabled());

        return associate;
    }

    public static Associate withId(Long associateId) {
        Associate associate = new Associate();
        associate.setId(associateId);

        return associate;
    }
}
