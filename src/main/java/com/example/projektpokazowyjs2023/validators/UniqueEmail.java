package com.example.projektpokazowyjs2023.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Adnotacja walidująca unikalność emaila użytkownika
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmailUniquenessValidator.class, EditEmailUniquenessValidator.class})
public @interface UniqueEmail {
    String message() default "{email.unique.error}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}