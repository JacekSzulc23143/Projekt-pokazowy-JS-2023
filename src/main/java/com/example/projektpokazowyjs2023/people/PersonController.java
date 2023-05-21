package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.authorities.Authority;
import com.example.projektpokazowyjs2023.authorities.AuthorityRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonRepository personRepository;
    private final PersonService personService;
    private final AuthorityRepository authorityRepository;

    public PersonController(PersonRepository personRepository, PersonService personService, AuthorityRepository authorityRepository) {
        this.personRepository = personRepository;
        this.personService = personService;
        this.authorityRepository = authorityRepository;
    }

    @GetMapping
    @Secured({"ROLE_MANAGE_USERS", "ROLE_USER_TAB"})
    ModelAndView index(@ModelAttribute PersonFilter filter, Pageable pageable) { // ModelAndView skrót, który pomaga
        // pracować na zmiennych
        ModelAndView modelAndView = new ModelAndView("people/index"); // referencja do pliku

        modelAndView.addObject("person", personRepository.findAll(pageable)); // zwróci listę wszystkich użytkowników
        // zapisanych w bazie danych
        modelAndView.addObject("filter", filter);
        return modelAndView;
    }

    // wyświetlenie formularza
    @GetMapping("/create")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("people/create");

        List<Authority> authorities = (List<Authority>) authorityRepository.findAll();

        Person person = new Person();
        modelAndView.addObject("authorities", authorities);
        modelAndView.addObject("person", person);

        return modelAndView;
    }

    // edycja formularza
    /**
     * https://www.baeldung.com/spring-boot-crud-thymeleaf
     */
    @GetMapping("/edit/{id}")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("people/create");

        Person person = personRepository.findById(id).orElse(null);

        modelAndView.addObject("authorities", authorityRepository.findAll());
        modelAndView.addObject("person", person);

        return modelAndView;
    }

    // wysłanie formularza do akcji save
    // dostęp do błędów przez klasę BindingResult
    // komunikaty przez klasę RedirectAttributes
    /**
     * Dokumentacja -> https://spring.io/guides/gs/handling-form-submission/
     */
    @PostMapping("/save")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView save(@ModelAttribute @Valid Person person, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

        ModelAndView modelAndView = new ModelAndView("people/create");

        // obsługa błędów
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("authorities", authorityRepository.findAll());
            modelAndView.addObject("person", person);
            modelAndView.addObject("status", "error");
            return modelAndView;
        }

        boolean isNew = person.getId() == null; // sprawdza czy nowy użytkownik

        personService.savePerson(person);

        redirectAttrs.addFlashAttribute("status", "success");

        if (isNew) { // podejmuje decyzje dokąd przenieść
            modelAndView.setViewName("redirect:/people");
        } else {
            modelAndView.setViewName("redirect:/people/edit/" + person.getId());
        }

//        // zmień widok na:
//        modelAndView.setViewName("redirect:/people");

        return modelAndView;
    }

    // usuwanie użytkownika
    // komunikaty przez klasę RedirectAttributes
    @GetMapping("/delete/{id}")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView deletePerson(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView("people/create");

        Person person = personRepository.findById(id).orElse(null);

        person.setEnabled(false);

        personRepository.delete(person);

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/people");

        return modelAndView;
    }
}