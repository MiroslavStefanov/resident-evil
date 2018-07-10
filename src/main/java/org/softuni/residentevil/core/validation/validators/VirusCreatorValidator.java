package org.softuni.residentevil.core.validation.validators;

import org.softuni.residentevil.core.validation.annotations.VirusCreator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class VirusCreatorValidator implements ConstraintValidator<VirusCreator, String> {
    private static final String[] validCreatorNames = {"Corp", "corp"};

    @Override
    public boolean isValid(String creatorName, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(validCreatorNames).anyMatch(x -> x.equals(creatorName));
    }
}
