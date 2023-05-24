package com.example.projektpokazowyjs2023.validators;

import com.example.projektpokazowyjs2023.people.Person;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Klasa walidująca poprawność hasła
public class ValidPasswordsValidator implements ConstraintValidator<ValidPasswords, Person> {

    @Override
    public void initialize(ValidPasswords constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext ctx) {
        // Wymagajmy hasła podczas tworzenia użytkownika. Logiką, która zwróci nam błąd, jeżeli ktoś nie poda hasła
        // podczas tworzenia użytkownika - ale pozwoli na to podczas edycji.
        if (person.getPassword() == null || person.getPassword().equals("")) {
            if (person.getId() == null) {
                ctx.disableDefaultConstraintViolation();
                ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("password")
                        .addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }

        boolean passwordsAreValid = person.getPassword().equals(person.getRepeatedPassword());

        if (passwordsAreValid) {
            return true;
        } else { // Komunikat błędu. Kod przygotowujący kod błędu - "Hasła nie są zgodne".
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("repeatedPassword")
                    .addConstraintViolation();
            return false;
        }
    }
}