package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.authorities.Authority;
import com.example.projektpokazowyjs2023.authorities.AuthorityRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
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
    ModelAndView index(@ModelAttribute PersonFilter filter, Pageable pageable) { // ModelAndView skrót, który pomaga pracować na zmiennych
        ModelAndView modelAndView = new ModelAndView("people/index"); // referencja do pliku

        modelAndView.addObject("person", personRepository.findAll(filter.buildQuery(),pageable)); // zwróci listę wszystkich użytkowników
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
        modelAndView.addObject("person", person);
        modelAndView.addObject("authorities", authorities);

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

        return modelAndView;
    }

    // usuwanie użytkownika
    // komunikaty przez klasę RedirectAttributes
    @GetMapping("/delete/{id}")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView deletePerson(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView("people/create");

        personService.softDelete(personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe id użytkownika:" + id)));

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/people");

        return modelAndView;
    }

    // edycja formularza użytkownika przez admina
    /**
     * https://www.baeldung.com/spring-boot-crud-thymeleaf
     */
    @GetMapping("/editPersonForm/{id}")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView editPersonForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("people/editPerson");

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe id użytkownika: " + id));

        modelAndView.addObject("authorities", authorityRepository.findAll());
        modelAndView.addObject("personForm", new PersonForm(person));

        return modelAndView;
    }

    // wysłanie formularza użytkownika do akcji save przez admina
    @PostMapping("/updatePerson/{id}")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView updatePerson(@PathVariable Long id, @Valid PersonForm personForm, BindingResult bindingResult,
                              RedirectAttributes redirectAttrs) {

        ModelAndView modelAndView = new ModelAndView("people/editPerson");

        // obsługa błędów
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("authorities", authorityRepository.findAll());
            modelAndView.addObject("personForm", personForm);
            personForm.setId(id);
            modelAndView.addObject("status", "error");
            return modelAndView;
        }
        personService.savePerson(personForm);

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/people/editPersonForm/" + personForm.getId());

        return modelAndView;
    }

    // edycja hasła użytkownika przez admina
    @GetMapping("/editPassForm/{id}")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView editPassForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("people/editPassword");
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe id użytkownika: " + id));
        PasswordForm passwordForm = new PasswordForm(person);
        passwordForm.setId(id);
        modelAndView.addObject("passwordForm", passwordForm);
        return modelAndView;
    }

    // wysłanie formularza hasła użytkownika do akcji save przez admina
    @PostMapping("/updatePass/{id}")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView updatePass(@PathVariable Long id, @Valid PasswordForm passwordForm, BindingResult bindingResult,
                            RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView("people/editPassword");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("passwordForm", passwordForm);
            passwordForm.setId(id);
            modelAndView.addObject("status", "error");
            return modelAndView;
        }
        personService.updatePassword(passwordForm);

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/people/editPassForm/" + passwordForm.getId());
        return modelAndView;
    }

    @GetMapping("/myAccount")
    @Secured({"ROLE_USER_TAB", "ROLE_MANAGE_USERS", "ROLE_MANAGE_PROJECT", "ROLE_MANAGE_COMMENTS"})
    ModelAndView viewUserHome(@AuthenticationPrincipal Person person, Principal principal){
        ModelAndView modelAndView = new ModelAndView("people/myAccount");

        List<Authority> authorities = (List<Authority>) authorityRepository.findAll();

        modelAndView.addObject("authorities", authorities);
        modelAndView.addObject("person", personService.currentUser(principal.getName()));

        return modelAndView;
    }

    // edycja formularza użytkownika
    @GetMapping("/editUserForm/{id}")
    @Secured({"ROLE_USER_TAB", "ROLE_MANAGE_USERS", "ROLE_MANAGE_PROJECT", "ROLE_MANAGE_COMMENTS"})
    ModelAndView editUserForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("people/editUser");

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe id użytkownika: " + id));

        modelAndView.addObject("authorities", authorityRepository.findAll());
        modelAndView.addObject("personForm", new PersonForm(person));

        return modelAndView;
    }

    // wysłanie formularza użytkownika do akcji save
    @PostMapping("/updateUser/{id}")
    @Secured({"ROLE_USER_TAB", "ROLE_MANAGE_USERS", "ROLE_MANAGE_PROJECT", "ROLE_MANAGE_COMMENTS"})
    ModelAndView updateUser(@PathVariable Long id, @Valid PersonForm personForm, BindingResult bindingResult,
                            RedirectAttributes redirectAttrs) {

        ModelAndView modelAndView = new ModelAndView("people/editUser");

        // obsługa błędów
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("authorities", authorityRepository.findAll());
            modelAndView.addObject("personForm", personForm);
            personForm.setId(id);
            modelAndView.addObject("status", "error");
            return modelAndView;
        }
        personService.savePerson(personForm);

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/people/editUserForm/" + personForm.getId());

        return modelAndView;
    }

    // edycja hasła użytkownika
    @GetMapping("/editPasswordForm/{id}")
    @Secured({"ROLE_USER_TAB", "ROLE_MANAGE_USERS", "ROLE_MANAGE_PROJECT", "ROLE_MANAGE_COMMENTS"})
    ModelAndView editPasswordForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("people/changePassword");
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe id użytkownika: " + id));
        PasswordForm passwordForm = new PasswordForm(person);
        passwordForm.setId(id);
        modelAndView.addObject("passwordForm", passwordForm);
        return modelAndView;
    }

    // wysłanie formularza hasła użytkownika do akcji save
    @PostMapping("/updatePassword/{id}")
    @Secured({"ROLE_USER_TAB", "ROLE_MANAGE_USERS", "ROLE_MANAGE_PROJECT", "ROLE_MANAGE_COMMENTS"})
    ModelAndView updatePassword(@PathVariable Long id, @Valid PasswordForm passwordForm, BindingResult bindingResult,
                                RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView("people/changePassword");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("passwordForm", passwordForm);
            passwordForm.setId(id);
            modelAndView.addObject("status", "error");
            return modelAndView;
        }
        personService.updatePassword(passwordForm);

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/people/editPasswordForm/" + passwordForm.getId());
        return modelAndView;
    }
}