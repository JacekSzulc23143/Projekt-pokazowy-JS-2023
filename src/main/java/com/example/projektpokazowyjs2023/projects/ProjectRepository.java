package com.example.projektpokazowyjs2023.projects;

import com.example.projektpokazowyjs2023.issues.Issue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    //wyszukiwanie tylko aktywnych projektów w bazie danych:
    List<Project> findAllByEnabled(Boolean enabled);

    Object findAll(Specification<Issue> issueSpecification, Pageable pageable);
}
