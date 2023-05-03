package com.example.projektpokazowyjs2023.people;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long>, JpaRepository<Person, Long> {

    Optional<Person> findByUsername(String username);
}