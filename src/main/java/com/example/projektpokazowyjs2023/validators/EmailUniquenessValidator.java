package com.example.projektpokazowyjs2023.validators;

import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.people.PersonRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Klasa walidująca użytkownika po email
public class EmailUniquenessValidator implements ConstraintValidator<UniqueEmail, Person> {

    private final PersonRepository personRepository;

    public EmailUniquenessValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext ctx) {
        Person foundPerson = personRepository.findByEmail(person.getEmail());

        if (foundPerson == null) {
            return true;
        }

        boolean emailIsUnique = person.getId() != null && foundPerson.getId().equals(person.getId());

        if (!emailIsUnique) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("email")
                    .addConstraintViolation();
        }

        return emailIsUnique;
    }
}