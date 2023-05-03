package com.example.projektpokazowyjs2023.config;

import com.example.projektpokazowyjs2023.authorities.Authority;
import com.example.projektpokazowyjs2023.authorities.AuthorityName;
import com.example.projektpokazowyjs2023.authorities.AuthorityRepository;
import com.example.projektpokazowyjs2023.people.PersonService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Bootstrap automatyczne tworzenie wpisów po starcie aplikacji
@Service
public class Bootstrap implements InitializingBean {

    // Wstrzyknięcie AuthorityRepository... i PersonService
    private final AuthorityRepository authorityRepository;
    private final PersonService personService;

    public Bootstrap(AuthorityRepository authorityRepository, PersonService personService) {
        this.authorityRepository = authorityRepository;
        this.personService = personService;
    }

    // pętla for, iterujemy po wszystkich wartościach które znajdują się w Enum AuthorityName (value)
    @Override
    public void afterPropertiesSet() throws Exception {
        for (AuthorityName authorityName : AuthorityName.values()) {
            Optional<Authority> authority = authorityRepository.findByAuthority(authorityName);

            if (authority.isEmpty()) {
                Authority auth = new Authority(authorityName);
                authorityRepository.save(auth);
            }
        }

        // zapisywanie tego użytkownika (Administratora) wywołamy w klasie Bootstrap :
        personService.prepareAdminUser();
    }
}