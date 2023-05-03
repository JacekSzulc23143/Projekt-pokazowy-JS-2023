package com.example.projektpokazowyjs2023.issues;

import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.projects.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;


@Data // zastępuje kilka adnotacji jedną np. Getter, Setter
@Entity // adnotacja: hibernate automatycznie utworzy tabelkę Issue
public class Issue {

    @ManyToOne(optional = false) // Ze strony Issue do Project. Optional wymagana relacja.
    @JoinColumn(name = "project_id", nullable = false) // Nazwa kolumny w tabelce issue. Kolumna nie może być nullem.
    private Project project;

    @ManyToOne(optional = false) // Ze strony Person do Project. Optional wymagana relacja.
    @JoinColumn(name = "contractor_id", nullable = false) // Nazwa kolumny w tabelce issue. Kolumna nie może być nullem.
    private Person contractor;

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
    @Column(columnDefinition = "TEXT") // dowolna liczba znaków tekstowych w polu tekstowym
    private String description;

    @Column(nullable = false) // pole obowiązkowe
    private Date dateCreated = new Date();

//    @Column(nullable = false)
    private Date lastUpdate;

    @Column(nullable = false)
    private Boolean enabled = true; // nowe zadanie domyślnie jest włączone

//    @ManyToOne
//    @JoinColumn(name = "creator_id", nullable = false)
//    private Person creator; // twórca zgłoszenia
//
//    @ManyToOne
//    @JoinColumn(name = "assignee_id")
//    private Person assignee; // osoba która ma zadanie wykonać

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
}