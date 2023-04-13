package com.example.projektpokazowyjs2023.people;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // TODO: @Secured("ROLE_PROJECTS_TAB")
    @GetMapping
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
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("people/create");

        Person person = new Person();
        modelAndView.addObject("person", person);

        return modelAndView;
    }

    // edycja formularza
    /**
     * https://www.baeldung.com/spring-boot-crud-thymeleaf
     */
    // TODO: @Secured("ROLE_PROJECT_EDIT")
    @GetMapping("/edit/{id}")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("people/create");

        Person person = personRepository.findById(id).orElse(null);

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
    ModelAndView save(@ModelAttribute @Valid Person person, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

        ModelAndView modelAndView = new ModelAndView("people/create");

        // obsługa błędów
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("person", person);
            modelAndView.addObject("status", "error");
            return modelAndView;
        }

        boolean isNew = person.getId() == null; // sprawdza czy nowy użytkownik

        personRepository.save(person);

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
