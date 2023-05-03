package com.example.projektpokazowyjs2023.authorities;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Optional<Authority> findByAuthority(AuthorityName authorityName);
}
