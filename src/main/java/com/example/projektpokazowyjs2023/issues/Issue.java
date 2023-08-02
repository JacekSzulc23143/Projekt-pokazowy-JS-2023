package com.example.projektpokazowyjs2023.issues;

import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.projects.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@Data // zastępuje kilka adnotacji jedną np. Getter, Setter
@Entity // adnotacja: hibernate automatycznie utworzy tabelkę Issue
@EntityListeners(AuditingEntityListener.class) // listener który obsługuje rewizję
public class Issue {

    @Id
    @GeneratedValue
    public Long id; // id generowane automatycznie

    @Audited
    @NotEmpty
//    @Size(min = 5)
    public String name;

    @NotEmpty
    @Size(min = 3, max = 7)
    public String code;

    @NotEmpty
//    @Size(min = 10)
    @Column(columnDefinition = "TEXT") // dowolna liczba znaków tekstowych w polu tekstowym
    public String description;

    @CreatedDate
    @Column(updatable = false)
    public Date dateCreated;

    @LastModifiedDate
    @Column
    public Date lastUpdated;

    @CreatedBy
    @Column(updatable = false)
    public String createdBy;

    @LastModifiedBy
    @Column
    public String lastUpdatedBy;

    @Column(nullable = false) // pole obowiązkowe
    public Boolean enabled = true; // nowe zadanie domyślnie jest włączone

    @ManyToOne(optional = false) // Ze strony Issue do Project. Optional wymagana relacja.
    @JoinColumn(name = "project_id", nullable = false) // Nazwa kolumny w tabelce issue. Kolumna nie może być nullem.
    public Project project;

    @ManyToOne(optional = false) // Ze strony Person do Project. Optional wymagana relacja.
    @JoinColumn(name = "assignee_id", nullable = false) // Nazwa kolumny w tabelce issue. Kolumna nie może być nullem.
    public Person assignee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    public Person creator; // twórca zgłoszenia

    // kolumna w tabelce z domyślnym typem zgłoszenia
    @Column(nullable = false) // pole obowiązkowe
    @Enumerated(EnumType.STRING) // w bazie danych pojawi się wyraz zamiast nr
    public IssueType type = IssueType.BUG;

    // kolumna w tabelce z domyślnym priorytetem zgłoszenia
    @Column(nullable = false) // pole obowiązkowe
    @Enumerated(EnumType.STRING) // w bazie danych pojawi się wyraz zamiast nr
    public IssuePriority priority = IssuePriority.EASY;

    // kolumna w tabelce z domyślnym stanem zgłoszenia
    @Audited
    @Column(nullable = false) // pole obowiązkowe
    @Enumerated(EnumType.STRING) // w bazie danych pojawi się wyraz zamiast nr
    public IssueState state = IssueState.OPEN;
}