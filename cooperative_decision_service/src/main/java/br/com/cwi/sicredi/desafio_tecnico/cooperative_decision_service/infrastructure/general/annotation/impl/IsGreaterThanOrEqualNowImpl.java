package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.annotation.impl;


import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.annotation.IsGreaterThanOrEqualNow;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class IsGreaterThanOrEqualNowImpl implements ConstraintValidator<IsGreaterThanOrEqualNow, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime dateTime, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime dateTimeWithoutSeconds = LocalDateTime.now().withSecond(0).withNano(0);

        return dateTime.isEqual(dateTimeWithoutSeconds) || dateTime.isAfter(dateTimeWithoutSeconds);
    }
}
