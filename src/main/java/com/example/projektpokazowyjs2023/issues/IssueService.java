package com.example.projektpokazowyjs2023.issues;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    public void softDelete(Issue issue){
        issue.setEnabled(false);
        issueRepository.save(issue);
    }
}
