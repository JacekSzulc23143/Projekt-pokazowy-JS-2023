package com.example.projektpokazowyjs2023.projects;

import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.people.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PersonRepository personRepository;

    // metoda, która miękko usuwa projekt
    public void softDelete(Project project){
        project.setEnabled(false);
        projectRepository.save(project);
    }

    public List<Project> findAllEnabled() {
        return projectRepository.findAllByEnabled(true);
    }

    public Set<Person> findAllCreators() {
        return findAllEnabled()
                .stream()
                .map(Project::getCreator)
                .collect(Collectors.toSet());
    }

    // metoda, która zapisuje twórcę do projektu
    public Project save(Project project, String creatorName) throws ParseException {
        Person creator = personRepository.findByUsername(creatorName);
        project.setCreator(creator);
        return projectRepository.save(project);
    }
}
