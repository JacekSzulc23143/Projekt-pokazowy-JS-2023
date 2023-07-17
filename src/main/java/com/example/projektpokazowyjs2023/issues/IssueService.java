package com.example.projektpokazowyjs2023.issues;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

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
    }

    // metoda, która aktualizuje priorytet zgłoszenia
    public void updatePriority(Long id, IssuePriority priority) {
        Issue issue = issueRepository.getReferenceById(id);
        issue.setPriority(priority);
        issueRepository.save(issue);
    }
}
