package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.authorities.Authority;
import com.example.projektpokazowyjs2023.authorities.AuthorityRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class PersonService {

    private final AuthorityRepository authorityRepository;
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PersonService(AuthorityRepository authorityRepository, PersonRepository personRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authorityRepository = authorityRepository;
        this.personRepository = personRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // Administrator
    // Dodajmy do naszej aplikacji pierwszego użytkownika — administratora.
    public void prepareAdminUser() {
        // Nie twórzmy użytkownika, jeśli już taki istnieje
        if (personRepository.findByUsername("admin").isPresent()) {
            return;
        }
        //TODO: Login i hasło weźmy ze zmiennej
        Person person = new Person("admin", "1234", "Administrator", "admin@admin.pl", true);

        // Hasło administratora zostanie zahashowane przed zapisaniem.
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));

        List<Authority> authorities = (List<Authority>) authorityRepository.findAll();
        person.setAuthorities(new HashSet<>(authorities));

        personRepository.save(person);
    }
}