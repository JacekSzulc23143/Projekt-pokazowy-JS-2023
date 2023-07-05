package com.example.projektpokazowyjs2023.projects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public void softDelete(Project project){
        project.setEnabled(false);
        projectRepository.save(project);
    }
}
