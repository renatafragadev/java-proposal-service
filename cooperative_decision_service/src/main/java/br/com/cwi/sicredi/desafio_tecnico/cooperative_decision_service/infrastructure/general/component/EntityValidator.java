package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.component;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator.I18nMessage;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.ConflictException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.NotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class EntityValidator {

    public void isConflicting(boolean entityIsPresent, String resource, String field) {
        if (entityIsPresent) {
            throw new ConflictException(I18nMessage.CONFLICT.getKey(), resource, field);
        }
    }

    public void isNonexistent(boolean entityIsPresent, String resource) {
        if (!entityIsPresent) {
            throw new NotFoundException(I18nMessage.NOT_FOUND.getKey(), resource);
        }
    }
}
