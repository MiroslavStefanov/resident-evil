package org.softuni.residentevil.core.validation.validators;

import org.softuni.residentevil.core.validation.annotations.PastDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class PastDateValidator implements ConstraintValidator<PastDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate == null || localDate.isBefore(LocalDate.now());
    }
}
