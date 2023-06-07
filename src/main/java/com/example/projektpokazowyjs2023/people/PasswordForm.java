package com.example.projektpokazowyjs2023.people;

import com.example.projektpokazowyjs2023.validators.ValidPasswords;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ValidPasswords
@NoArgsConstructor
public class PasswordForm {

//    @Id
//    @GeneratedValue
    public Long id;

    @NotEmpty
//    @Size(min = 5)
//    @Column(nullable = false)
    public String password;

    @Transient // @Transient oznacza że nie zostanie zapisane w bazie danych
    public String repeatedPassword;

    @Column(nullable = false)
//    @ColumnDefault(value = "true")
    public Boolean enabled = true;

    public PasswordForm(Person person) {
        this.password = person.password;
        this.repeatedPassword = person.repeatedPassword;
    }
}