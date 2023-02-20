package com.example.projektpokazowyjs2023.projects;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data // zastępuje kilka adnotacji jedną np. Getter, Setter
@Entity // adnotacja: hibernate automatycznie utworzy tabelkę Project
public class Project {

    @Id
    @GeneratedValue
    Long id; // id generowane automatycznie

    private String name;

    private String code;

    private String description;

    private Date dateCreated = new Date();

    private Boolean enabled = true;

}
