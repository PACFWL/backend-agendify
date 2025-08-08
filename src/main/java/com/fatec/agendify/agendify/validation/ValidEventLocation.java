package com.fatec.agendify.agendify.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EventLocationValidator.class)
@Target({ ElementType.TYPE }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEventLocation {
    String message() default "Local do evento é incompatível com a modalidade escolhida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}