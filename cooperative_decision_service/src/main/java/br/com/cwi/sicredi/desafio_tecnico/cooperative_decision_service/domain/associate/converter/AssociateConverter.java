package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.converter;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.model.AssociateModel;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.request.AssociateRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

public class AssociateConverter {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Associate toEntity(AssociateRequest associateRequest) {
        Associate associate = new Associate();
        associate.setDocument(associateRequest.getDocument());
        associate.setName(associateRequest.getName());
        associate.setBirthDate(associateRequest.getBirthDate());

        return associate;
    }

    public static Associate withId(Long associateId) {
        Associate associate = new Associate();
        associate.setId(associateId);

        return associate;
    }

    public static AssociateModel toModel(Associate associate) {
        AssociateModel associateModel = new AssociateModel();
        associateModel.setId(associate.getId());
        associateModel.setUuid(associate.getUuid());
        associateModel.setDocument(associate.getDocument());
        associateModel.setName(associate.getName());
        associateModel.setBirthDate(associate.getBirthDate().format(DATE_FORMAT));
        associateModel.setEnabled(associate.getEnabled());

        return associateModel;
    }

    public static Collection<AssociateModel> toListModel(Collection<Associate> associateList) {
        return associateList.stream().map(AssociateConverter::toModel).collect(Collectors.toList());
    }
}
