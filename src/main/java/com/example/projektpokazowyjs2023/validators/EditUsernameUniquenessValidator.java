package com.example.projektpokazowyjs2023.validators;

import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.people.PersonForm;
import com.example.projektpokazowyjs2023.people.PersonRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Klasa walidująca użytkownika po username w moim koncie
public class EditUsernameUniquenessValidator implements ConstraintValidator<UniqueUsername, PersonForm> {

    private final PersonRepository personRepository;

    public EditUsernameUniquenessValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PersonForm personform, ConstraintValidatorContext ctx) {
        Person foundPerson = personRepository.findByUsername(personform.getUsername());

        if (foundPerson == null) {
            return true;
        }

        boolean usernameIsUnique = personform.getId() != null && foundPerson.getId().equals(personform.getId());

        if (!usernameIsUnique) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("username")
                    .addConstraintViolation();
        }

        return usernameIsUnique;
    }
}