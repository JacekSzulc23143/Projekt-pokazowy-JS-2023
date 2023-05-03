package com.example.projektpokazowyjs2023.config;

import com.example.projektpokazowyjs2023.authorities.Authority;
import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.people.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Wczytanie użytkownika
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Pobieramy użytkownika z bazy danych. Optional bo może nie istnieć w bazie danych.
        Optional<Person> person = personRepository.findByUsername(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        Person existingPerson = person.get();

        List<GrantedAuthority> authorities = findUserAuthorities(existingPerson);

        // Wysyłamy użytkownika do Spring Security w odpowiedniej formie
        return new User(existingPerson.getUsername(),existingPerson.getPassword(),authorities);
    }

    // Lista uprawnień.
    // Metoda która przygotuje getAuthorities
    private List<GrantedAuthority> findUserAuthorities(Person person) {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        // Znajdujemy wszystkie uprawnienia użytkownika w bazie danych
        for (Authority authority : person.getAuthorities()) {
            // Przygotowany GrantedAuthority i dodany do listy
            String authName = authority.getAuthority().name();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authName);
            grantedAuthorities.add(grantedAuthority);
        }
        // Dodajemy do listy do zbioru obiektów typu GrantedAuthority
        return new ArrayList<>(grantedAuthorities);
    }
}