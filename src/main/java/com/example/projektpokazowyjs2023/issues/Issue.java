package com.example.projektpokazowyjs2023.issues;

import com.example.projektpokazowyjs2023.projects.Project;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data // zastępuje kilka adnotacji jedną np. Getter, Setter
@Entity // adnotacja: hibernate automatycznie utworzy tabelkę Issue
public class Issue {

    @ManyToOne(optional = false) // Ze strony Issue do Project. Optional wymagana relacja.
    @JoinColumn(name = "project_id", nullable = false) // Nazwa kolumny w tabelce issue. Kolumna nie może być nullem.
    private Project project;

    @Id
    @GeneratedValue
    private Long id; // id generowane automatycznie

    private String name;

    private String code;

    private String description;

    private Date dateCreated = new Date();

    private Date lastUpdate;

    private Boolean enabled = true;

    // kolumna w tabelce z domyślnym typem zgłoszenia
    @Column(nullable = false) // pole obowiązkowe
    @Enumerated(EnumType.STRING) // w bazie danych pojawi się wyraz zamiast nr
    private IssueType type = IssueType.BUG;
    // kolumna w tabelce z domyślnym priorytetem zgłoszenia
    @Column(nullable = false) // pole obowiązkowe
    @Enumerated(EnumType.STRING) // w bazie danych pojawi się wyraz zamiast nr
    private IssuePriority priority = IssuePriority.EASY;

    // kolumna w tabelce z domyślnym stanem zgłoszenia
    @Column(nullable = false) // pole obowiązkowe
    @Enumerated(EnumType.STRING) // w bazie danych pojawi się wyraz zamiast nr
    private IssueState state = IssueState.OPEN;

    // TODO: kolumna w tabelce z domyślnym wykonawcą zgłoszenia
    // TODO: tłumaczenia enumów
}
