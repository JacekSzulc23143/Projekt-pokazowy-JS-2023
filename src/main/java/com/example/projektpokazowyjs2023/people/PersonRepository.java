package com.example.projektpokazowyjs2023.people;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<Person, Long>, JpaRepository<Person, Long> {

    Person findByUsername(String username);
}