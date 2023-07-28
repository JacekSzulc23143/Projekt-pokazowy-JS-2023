package com.example.projektpokazowyjs2023.issues;

import com.example.projektpokazowyjs2023.mail.MailService;
import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.people.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.projektpokazowyjs2023.issues.IssueState.DONE;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final PersonRepository personRepository;
    private final MailService mailService;

    // metoda, która miękko usuwa zgłoszenie
    public void softDelete(Issue issue){
        issue.setEnabled(false);
        issueRepository.save(issue);
    }

    // metoda, która aktualizuje stan zgłoszenia
    public void updateState(Long id, IssueState state) {
        Issue issue = issueRepository.getReferenceById(id);
        issue.setState(state);
        issueRepository.save(issue);
        if (state == DONE) {
            mailService.sendToCreator(issue);
        }
    }

    // metoda, która aktualizuje priorytet zgłoszenia
    public void updatePriority(Long id, IssuePriority priority) {
        Issue issue = issueRepository.getReferenceById(id);
        issue.setPriority(priority);
        issueRepository.save(issue);
    }

    // metoda, która aktualizuje typ zgłoszenia
    public void updateType(Long id, IssueType type) {
        Issue issue = issueRepository.getReferenceById(id);
        issue.setType(type);
        issueRepository.save(issue);
    }

    public List<Issue> findAllEnabled() {
        return issueRepository.findAllByEnabled(true);
    }

    public Set<Person> findAllCreators() {
        return findAllEnabled()
                .stream()
                .map(Issue::getCreator)
                .collect(Collectors.toSet());
    }

    // metoda, która zapisuje twórcę do zgłoszenia
    public Issue save(Issue issue, String creatorName) throws ParseException {
        Person creator = personRepository.findByUsername(creatorName);
        issue.setCreator(creator);
        mailService.sendToContractor(issue);
        return issueRepository.save(issue);
    }

    // metoda, która aktualizuje twórcę do zgłoszenia
    public Issue updateIssue(Issue issue, String creatorName) throws ParseException {
        Person creator = personRepository.findByUsername(creatorName);
        issue.setCreator(creator);
        return issueRepository.save(issue);
    }
}
