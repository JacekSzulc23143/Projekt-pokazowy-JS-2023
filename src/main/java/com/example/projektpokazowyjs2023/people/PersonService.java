package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.authorities.Authority;
import com.example.projektpokazowyjs2023.authorities.AuthorityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class PersonService {

    private final AuthorityRepository authorityRepository;
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${my.admin.username}")
    private String myAdminUsername;

    @Value("${my.admin.password}")
    private String myAdminPassword;

    public PersonService(AuthorityRepository authorityRepository, PersonRepository personRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authorityRepository = authorityRepository;
        this.personRepository = personRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // Administrator
    // Dodajmy do naszej aplikacji pierwszego użytkownika — administratora.
    public void prepareAdminUser() {
        // Nie twórzmy użytkownika, jeśli już taki istnieje
        if (personRepository.findByUsername(myAdminUsername) != null) {
            System.out.println("Użytkownik " + myAdminUsername + " już istnieje. Przerywamy tworzenie.");
            return;
        }

        System.out.println("Tworzymy administratora: " + myAdminUsername + "...");

        Person person = new Person(myAdminUsername, myAdminPassword, "Administrator", "admin@email.pl", true);

        List<Authority> authorities = (List<Authority>) authorityRepository.findAll();
        person.setAuthorities(new HashSet<>(authorities));

        savePerson(person);
    }

    // Hash hasła
    protected void savePerson(Person person) {
        String hashedPassword = bCryptPasswordEncoder.encode(person.getPassword());
        person.setPassword(hashedPassword);

        personRepository.save(person);
    }

    // Aktualny użytkownik
    public Person currentUser(String username) {
        return personRepository.findByUsername(username);
    }

    public void savePerson(PersonForm personForm) {
        Person person = personRepository.findById(personForm.id).orElse(null);
        person.username = personForm.username;
        person.realName = personForm.realName;
        person.email = personForm.email;
        if (personForm.authorities.size() != 0) {
            person.authorities = personForm.authorities;
        }
        personRepository.save(person);
    }

    public void updatePassword(PasswordForm passwordForm) {
        Person person = personRepository.findById(passwordForm.id).orElse(null);
        String pass = bCryptPasswordEncoder.encode(passwordForm.password);
        person.setPassword(pass);
        personRepository.save(person);
    }

    public void softDelete(Person person){
        person.setEnabled(false);
        person.authorities.clear();
        person.setPassword("");
        personRepository.save(person);
    }
}