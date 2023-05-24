package com.example.projektpokazowyjs2023.validators;

import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.people.PersonRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Klasa walidująca użytkownika po username
public class UsernameUniquenessValidator implements ConstraintValidator<UniqueUsername, Person> {

    private final PersonRepository personRepository;

    public UsernameUniquenessValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext ctx) {
        Person foundPerson = personRepository.findByUsername(person.getUsername());

        if (foundPerson == null) {
            return true;
        }

        boolean usernameIsUnique = person.getId() != null && foundPerson.getId().equals(person.getId());

        if (!usernameIsUnique) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("username")
                    .addConstraintViolation();
        }

        return usernameIsUnique;
    }
}