package com.example.projektpokazowyjs2023.people;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
//    @Size(min = 5)
    @Column(nullable = false, unique = true)
    private String login;

    @NotEmpty
//    @Size(min = 5)
    @Column(nullable = false)
    private String password;

    @NotEmpty
//    @Size(min = 2)
    @Column(nullable = false)
    private String realName;

//    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean enabled = true;

//    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable(name = "person_authorities",
//            joinColumns = @JoinColumn(name = "person_id"),
//            inverseJoinColumns = @JoinColumn(name = "authority_id"))
//
//    private Set<Authority> authorities;

}
