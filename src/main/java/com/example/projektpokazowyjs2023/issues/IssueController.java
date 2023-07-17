package com.example.projektpokazowyjs2023.issues;

import com.example.projektpokazowyjs2023.audit.AuditDataDTO;
import com.example.projektpokazowyjs2023.mail.MailService;
import com.example.projektpokazowyjs2023.people.PersonRepository;
import com.example.projektpokazowyjs2023.projects.ProjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final ProjectRepository projectRepository; // repozytorium projektów
    private final IssueRepository issueRepository; // repozytorium zgłoszeń
    private final PersonRepository personRepository; // repozytorium użytkowników
    private final MailService mailService;
    private final EntityManager entityManager; // wymaga AuditReader, żeby dostać się do bazy danych
    private final IssueService issueService;

    public IssueController(ProjectRepository projectRepository, IssueRepository issueRepository,
                           PersonRepository personRepository, MailService mailService, EntityManager entityManager, IssueService issueService) {
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.personRepository = personRepository;
        this.mailService = mailService;
        this.entityManager = entityManager;
        this.issueService = issueService;
    }

    @GetMapping
    @Secured({"ROLE_MANAGE_PROJECT", "ROLE_USER_TAB", "ROLE_MANAGE_COMMENTS"})
    ModelAndView index(@ModelAttribute IssueFilter filter, Pageable pageable) { // ModelAndView skrót, który pomaga pracować na zmiennych
        ModelAndView modelAndView = new ModelAndView("issues/index"); // referencja do pliku

        modelAndView.addObject("issues", issueRepository.findAll(filter.buildQuery(), pageable)); // zwróci listę wszystkich
        // zgłoszeń zapisanych w bazie danych
        modelAndView.addObject("projects", projectRepository.findAllByEnabled(true));
        modelAndView.addObject("states", IssueState.values());
        modelAndView.addObject("people", personRepository.findAllByEnabled(true));
        modelAndView.addObject("filter", filter);
        return modelAndView;
    }

    // wyświetlenie formularza zgłoszeń
    @GetMapping("/create")
    @Secured("ROLE_MANAGE_PROJECT")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("issues/create");

        Issue issue = new Issue();
        modelAndView.addObject("issue", issue);
        modelAndView.addObject("projects", projectRepository.findAllByEnabled(true));
        modelAndView.addObject("people", personRepository.findAllByEnabled(true));

        return modelAndView;
    }

    // wysłanie formularza do akcji save
    // dostęp do błędów przez klasę BindingResult
    // komunikaty przez klasę RedirectAttributes
    @PostMapping("/save")
    @Secured("ROLE_MANAGE_PROJECT")
    ModelAndView save(@ModelAttribute @Valid Issue issue, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

        ModelAndView modelAndView = new ModelAndView("issues/create");

        // obsługa błędów
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("issue", issue);
            modelAndView.addObject("projects", projectRepository.findAllByEnabled(true));
            modelAndView.addObject("people", personRepository.findAllByEnabled(true));
            modelAndView.addObject("status", "error");
            return modelAndView;
        }

        mailService.sendToContractor(issue);

        issueRepository.save(issue);

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/issues");

        return modelAndView;
    }

    // edycja formularza zgłoszeń
    @GetMapping("/edit/{id}")
    @Secured({"ROLE_MANAGE_PROJECT", "ROLE_USER_TAB", "ROLE_MANAGE_COMMENTS"})
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("issues/update");

        Issue issue = issueRepository.findById(id).orElse(null);

        modelAndView.addObject("issue", issue);
        modelAndView.addObject("projects", projectRepository.findAllByEnabled(true));
        modelAndView.addObject("people", personRepository.findAllByEnabled(true));

        return modelAndView;
    }

    // wysłanie formularza do akcji updateIssue
    // dostęp do błędów przez klasę BindingResult
    // komunikaty przez klasę RedirectAttributes
    @PostMapping("/updateIssue")
    @Secured({"ROLE_MANAGE_PROJECT", "ROLE_USER_TAB"})
    ModelAndView updateIssue(@ModelAttribute @Valid Issue issue, BindingResult bindingResult,
                             RedirectAttributes redirectAttrs) {

        ModelAndView modelAndView = new ModelAndView("issues/update");

        // obsługa błędów
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("issue", issue);
            modelAndView.addObject("projects", projectRepository.findAllByEnabled(true));
            modelAndView.addObject("people", personRepository.findAllByEnabled(true));
            modelAndView.addObject("status", "error");
            return modelAndView;
        }

        issueRepository.save(issue);

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/issues/edit/" + issue.getId());

        return modelAndView;
    }

    // usuwanie zgłoszenia
    // komunikaty przez klasę RedirectAttributes
    @GetMapping("/delete/{id}")
    @Secured("ROLE_MANAGE_PROJECT")
    ModelAndView deleteIssue(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView("issues/create");

        issueService.softDelete(issueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe id zgłoszenia:" + id)));

        redirectAttrs.addFlashAttribute("status", "success");

        modelAndView.setViewName("redirect:/issues");

        return modelAndView;
    }

    // Historia wybranego zgłoszenia
    @GetMapping("/history/{id}")
    @Secured({"ROLE_MANAGE_PROJECT", "ROLE_USER_TAB", "ROLE_MANAGE_COMMENTS"})
    ModelAndView history(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("issues/history");

        Issue issue = issueRepository.findById(id).orElse(null);

        modelAndView.addObject("issue", issue);
        modelAndView.addObject("projects", projectRepository.findAllByEnabled(true));
        modelAndView.addObject("people", personRepository.findAllByEnabled(true));

        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        // Zapytanie, które zwraca listę wszystkich rewizji
        List<Object[]> rawRevisions = auditReader.createQuery()
                .forRevisionsOfEntity(Issue.class, false, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();

        List<AuditDataDTO> revisions = rawRevisions.stream().map(AuditDataDTO::new).toList();
        modelAndView.addObject("revisions", revisions);

        return modelAndView;
    }

    // end point (punkt przerwania-końcowy) wywoływany w momencie, gdy zmienimy coś w selekcie po stanie zgłoszenia
    // metoda wykonująca się asynchronicznie, wywoływana przez JavaScript
    @PatchMapping("/state/{id}")
    ResponseEntity<Void> updateState(@PathVariable Long id, @RequestBody IssueState state) {
        issueService.updateState(id, state);
        return ResponseEntity.ok().build();
    }

    // end point (punkt przerwania-końcowy) wywoływany w momencie, gdy zmienimy coś w selekcie po priorytecie zgłoszenia
    // metoda wykonująca się asynchronicznie, wywoływana przez JavaScript
    @PatchMapping("/priority/{id}")
    ResponseEntity<Void> updatePriority(@PathVariable Long id, @RequestBody IssuePriority priority) {
        issueService.updatePriority(id, priority);
        return ResponseEntity.ok().build();
    }
}
