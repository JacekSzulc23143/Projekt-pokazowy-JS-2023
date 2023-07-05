package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.issues.Issue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>, JpaRepository<Person, Long> {

    Person findByUsername(String username);

    Person findByEmail(String email);

    List<Person> findAllByEnabled(Boolean enabled);

    Object findAll(Specification<Issue> issueSpecification, Pageable pageable);
}