package com.example.projektpokazowyjs2023.projects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data // zastępuje kilka adnotacji jedną np. Getter, Setter
@Entity // adnotacja: hibernate automatycznie utworzy tabelkę Project
public class Project {

    @Id
    @GeneratedValue
    private Long id; // id generowane automatycznie

    @NotEmpty
//    @Size(min = 5)
    private String name;

    @NotEmpty
//    @Size(min = 3)
    private String code;

    @NotEmpty
//    @Size(min = 10)
    private String description;

    private Date dateCreated = new Date();

    private Boolean enabled = true;

}
