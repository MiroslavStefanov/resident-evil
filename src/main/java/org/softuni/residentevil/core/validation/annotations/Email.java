package org.softuni.residentevil.core.validation.annotations;

import org.softuni.residentevil.core.validation.validators.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    String message() default "{org.softuni.resident-evil.core.validation.annotations.Email.default}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
