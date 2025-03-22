package com.fatec.agendify.agendify.validation;

import com.fatec.agendify.agendify.model.Event;
import com.fatec.agendify.agendify.model.EventMode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventLocationValidator implements ConstraintValidator<ValidEventLocation, Event> {

    @Override
    public boolean isValid(Event event, ConstraintValidatorContext context) {
        if (event == null) {
            return true; 
        }

        if (event.getMode() == null || event.getLocation() == null) {
            return true; 
        }

       
        if (event.getMode() == EventMode.ONLINE && !event.getLocation().getName().equalsIgnoreCase("ONLINE")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Eventos ONLINE devem ter local como ONLINE.")
                   .addPropertyNode("location")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }
}
