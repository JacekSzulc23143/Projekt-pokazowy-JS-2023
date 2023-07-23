package com.example.projektpokazowyjs2023.issues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

    //wyszukiwanie tylko aktywnych zgłoszeń w bazie danych:
    List<Issue> findAllByEnabled(Boolean enabled);
}
