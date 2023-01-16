package com.example.projektpokazowyjs2023.projects;

import jakarta.persistence.*;
import lombok.Data;

@Data // zastępuje kilka adnotacji jedną np. Getter, Setter
@Entity // adnotacja: hibernate automatycznie utworzy tabelkę Project
public class Project {

    @Id
    @GeneratedValue
    private Long id; // id generowane automatycznie

    private String name;

    private String code;

    private String description;

    private Boolean enabled = true;

}
