package com.example.projektpokazowyjs2023.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Adnotacja walidująca unikalność nazwy użytkownika
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UsernameUniquenessValidator.class, EditUsernameUniquenessValidator.class})
public @interface UniqueUsername {
    String message() default "{username.unique.error}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}