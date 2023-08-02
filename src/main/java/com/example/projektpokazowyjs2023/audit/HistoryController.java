package com.example.projektpokazowyjs2023.audit;

import com.example.projektpokazowyjs2023.issues.Issue;
import com.example.projektpokazowyjs2023.issues.IssueRepository;
import com.example.projektpokazowyjs2023.people.PersonRepository;
import com.example.projektpokazowyjs2023.projects.ProjectRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/audit")
public class HistoryController {

    private final ProjectRepository projectRepository; // repozytorium projektów
    private final IssueRepository issueRepository; // repozytorium zgłoszeń
    private final PersonRepository personRepository; // repozytorium użytkowników
    private final EntityManager entityManager; // wymaga AuditReader, żeby dostać się do bazy danych


    // Historia wybranego zgłoszenia
    @GetMapping("/history/{id}")
    @Secured({"ROLE_MANAGE_PROJECT"})
    ModelAndView history(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("issues/history");

        Issue issue = issueRepository.findById(id).orElse(null);

        modelAndView.addObject("issue", issue);
        modelAndView.addObject("projects", projectRepository.findAllByEnabled(true));
        modelAndView.addObject("people", personRepository.findAllByEnabled(true));

//        return modelAndView;

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
}