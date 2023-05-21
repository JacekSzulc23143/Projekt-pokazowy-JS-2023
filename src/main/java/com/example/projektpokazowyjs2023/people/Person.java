package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.authorities.Authority;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
//    @Size(min = 5)
    @Column(nullable = false, unique = true)
    private String username;

    @NotEmpty
//    @Size(min = 5)
    @Column(nullable = false)
    private String password;

    @NotEmpty
//    @Size(min = 2)
    @Column(nullable = false)
    private String realName;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
//    @ColumnDefault(value = "true")
    private Boolean enabled = true;

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
    Set<Authority> authorities;

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
}