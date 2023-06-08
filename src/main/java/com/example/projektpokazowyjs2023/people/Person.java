package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.authorities.Authority;
import com.example.projektpokazowyjs2023.validators.UniqueEmail;
import com.example.projektpokazowyjs2023.validators.UniqueUsername;
import com.example.projektpokazowyjs2023.validators.ValidPasswords;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@UniqueUsername
@UniqueEmail
@ValidPasswords
public class Person {

    @Id
    @GeneratedValue
    public Long id;

    @NotEmpty
//    @Size(min = 5)
    @Column(nullable = false, unique = true)
    public String username;

    @NotEmpty
//    @Size(min = 2)
    @Column(nullable = false)
    public String realName;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(nullable = false, unique = true)
    public String email;

//    @NotEmpty
//    @Size(min = 5)
    @Column(nullable = false)
    public String password;

    @Transient // @Transient oznacza że nie zostanie zapisane w bazie danych
    public String repeatedPassword;

    @Column(nullable = false)
//    @ColumnDefault(value = "true")
    public Boolean enabled = true;

    public Person(String username, String password, String realName, String email, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.email = email;
        this.enabled = enabled;
    }

    public Person() {
    }

    //    Powiązanie wiele-do-wielu między Person a Authority (wykład Pawła)
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "person_authorities",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    Set<Authority> authorities = new HashSet<>();

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}