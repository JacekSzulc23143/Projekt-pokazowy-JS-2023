package com.example.projektpokazowyjs2023.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Adnotacja walidująca powtórzenie hasła
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidPasswordsValidator.class, EditValidPasswordsValidator.class})
public @interface ValidPasswords {

    String message() default "{passwords.not.valid.error}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
