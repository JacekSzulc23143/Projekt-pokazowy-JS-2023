package com.example.projektpokazowyjs2023.validators;

import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.people.PersonForm;
import com.example.projektpokazowyjs2023.people.PersonRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Klasa walidująca użytkownika po email
public class EditEmailUniquenessValidator implements ConstraintValidator<UniqueEmail, PersonForm> {

    private final PersonRepository personRepository;

    public EditEmailUniquenessValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PersonForm personform, ConstraintValidatorContext ctx) {
        Person foundPerson = personRepository.findByEmail(personform.getEmail());

        if (foundPerson == null) {
            return true;
        }

        boolean emailIsUnique = personform.getId() != null && foundPerson.getId().equals(personform.getId());

        if (!emailIsUnique) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("email")
                    .addConstraintViolation();
        }

        return emailIsUnique;
    }
}