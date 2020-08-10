package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.annotation;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.annotation.impl.IsGreaterThanOrEqualNowImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Repeatable(IsGreaterThanOrEqualNow.List.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsGreaterThanOrEqualNowImpl.class)
public @interface IsGreaterThanOrEqualNow {

    String message() default "{error.business.session.start_date_time.before_now}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    @interface List {
        IsGreaterThanOrEqualNow[] value();
    }
}
