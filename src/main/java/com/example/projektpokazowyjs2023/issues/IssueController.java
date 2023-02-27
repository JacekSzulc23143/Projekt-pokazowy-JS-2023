package com.example.projektpokazowyjs2023.issues;

import com.example.projektpokazowyjs2023.people.PersonRepository;
import com.example.projektpokazowyjs2023.projects.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final ProjectRepository projectRepository; // repozytorium projektów
    private final IssueRepository issueRepository; // repozytorium zgłoszeń
    private final PersonRepository personRepository; // repozytorium użytkowników

    public IssueController(ProjectRepository projectRepository, IssueRepository issueRepository,
                           PersonRepository personRepository) {
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.personRepository = personRepository;
    }

    @GetMapping
    ModelAndView index() { // ModelAndView skrót, który pomaga pracować na zmiennych
        ModelAndView modelAndView = new ModelAndView("issues/index"); // referencja do pliku

        modelAndView.addObject("issues", issueRepository.findAll()); // zwróci listę wszystkich zgłoszeń
        // zapisanych w bazie danych
        return modelAndView;
    }

    // wyświetlenie formularza zgłoszeń
    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("issues/create");

        Issue issue = new Issue();
        modelAndView.addObject("issue", issue);
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("people", personRepository.findAll());

        return modelAndView;
    }

    // edycja formularza zgłoszeń
    // TODO: @Secured("ROLE_PROJECT_EDIT")
    @GetMapping("/edit/{id}")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("issues/create");

        Issue issue = issueRepository.findById(id).orElse(null);

        modelAndView.addObject("issue", issue);
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("people", personRepository.findAll());

        return modelAndView;
    }

    // wysłanie formularza do akcji save
    // dostęp do błędów przez klasę BindingResult
    // komunikaty przez klasę RedirectAttributes
    @PostMapping("/save")
    ModelAndView save(@ModelAttribute @Valid Issue issue, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

        ModelAndView modelAndView = new ModelAndView("issues/create");

        // obsługa błędów
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("issue", issue);
            modelAndView.addObject("projects", projectRepository.findAll());
            modelAndView.addObject("people", personRepository.findAll());
            modelAndView.addObject("status", "error");
            return modelAndView;
        }

        boolean isNew = issue.getId() == null; // sprawdza czy nowe zgłoszenie

        issueRepository.save(issue);

        redirectAttrs.addFlashAttribute("status", "success");

        if (isNew) { // podejmuje decyzje dokąd przenieść
            modelAndView.setViewName("redirect:/issues");
        } else {
            modelAndView.setViewName("redirect:/issues/edit/" + issue.getId());
        }

//        // zmień widok na:
//        modelAndView.setViewName("redirect:/issues");

        return modelAndView;
    }

    // usuwanie zgłoszenia
    // komunikaty przez klasę RedirectAttributes
    @GetMapping("/delete/{id}")
    ModelAndView deleteIssue(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView("issues/create");

        Issue issue = issueRepository.findById(id).orElse(null);

        issue.setEnabled(false);

        issueRepository.delete(issue);

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/issues");

        return modelAndView;
    }
}
