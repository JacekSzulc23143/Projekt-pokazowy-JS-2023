package com.example.projektpokazowyjs2023.validators;

import com.example.projektpokazowyjs2023.people.PasswordForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Klasa walidująca poprawność hasła w moim koncie
public class EditValidPasswordsValidator implements ConstraintValidator<ValidPasswords, PasswordForm> {

    @Override
    public void initialize(ValidPasswords constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PasswordForm passwordForm, ConstraintValidatorContext ctx) {
        // Wymagajmy hasła podczas tworzenia użytkownika. Logiką, która zwróci nam błąd, jeżeli ktoś nie poda hasła
        // podczas tworzenia użytkownika - ale pozwoli na to podczas edycji.
        if (passwordForm.getPassword() == null || passwordForm.getPassword().equals("")) {
            if (passwordForm.getId() == null) {
                ctx.disableDefaultConstraintViolation();
                ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("password")
                        .addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }

        boolean passwordsAreValid = passwordForm.getPassword().equals(passwordForm.getRepeatedPassword());

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