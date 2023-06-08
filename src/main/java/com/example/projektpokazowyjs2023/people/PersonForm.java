package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.authorities.Authority;
import com.example.projektpokazowyjs2023.validators.UniqueEmail;
import com.example.projektpokazowyjs2023.validators.UniqueUsername;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@UniqueUsername
@UniqueEmail
@NoArgsConstructor
public class PersonForm {

//    @Id
//    @GeneratedValue
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

    @Column(nullable = false)
//    @ColumnDefault(value = "true")
    public Boolean enabled = true;

    public PersonForm(Person person) {
        this.id = person.id;
        this.username = person.username;
        this.realName = person.realName;
        this.email = person.email;
        this.authorities = person.authorities;
    }

    Set<Authority> authorities = new HashSet<>();
}
