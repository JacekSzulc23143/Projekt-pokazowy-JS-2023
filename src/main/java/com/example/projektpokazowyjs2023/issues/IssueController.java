package com.example.projektpokazowyjs2023.issues;

import com.example.projektpokazowyjs2023.projects.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final ProjectRepository projectRepository; // repozytorium projektów
    private final IssueRepository issueRepository; // repozytorium zgłoszeń

    public IssueController(ProjectRepository projectRepository, IssueRepository issueRepository) {
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
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

        return modelAndView;
    }

    // wysłanie formularza do akcji save
    // dostęp do błędów przez klasę BindingResult
    @PostMapping("/save")
    ModelAndView save(@ModelAttribute @Valid Issue issue, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("issues/create");

        // obsługa błędów
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("issue", issue);
            modelAndView.addObject("projects", projectRepository.findAll());
            return modelAndView;
        }

        boolean isNew = issue.getId() == null; // sprawdza czy nowe zgłoszenie

        issueRepository.save(issue);

        if (isNew) { // podejmuje decyzje dokąd przenieść
            modelAndView.setViewName("redirect:/issues");
        } else {
            modelAndView.setViewName("redirect:/issues/edit/" + issue.getId());
        }

        return modelAndView;
    }

    // usuwanie zgłoszenia
    @GetMapping("/delete/{id}")
    ModelAndView deleteIssue(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("issues/create");

        Issue issue = issueRepository.findById(id).orElse(null);

        issue.setEnabled(false);

        issueRepository.delete(issue);

        modelAndView.setViewName("redirect:/issues");

        return modelAndView;
    }
}
