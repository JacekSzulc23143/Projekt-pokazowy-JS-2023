package com.example.projektpokazowyjs2023.issues;

import com.example.projektpokazowyjs2023.people.Person;
import com.example.projektpokazowyjs2023.projects.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Data // zastępuje kilka adnotacji jedną np. Getter, Setter
@EntityListeners(AuditingEntityListener.class) // listener który obsługuje rewizję
@NoArgsConstructor
public class IssueForm {

    public Long id;

    public String name;

    public String code;

    @NotEmpty
    @Column(columnDefinition = "TEXT") // dowolna liczba znaków tekstowych w polu tekstowym
    public String description;

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

    public IssueForm(Issue issue) {
        this.id = issue.id;
        this.name = issue.name;
        this.code = issue.code;
        this.description = issue.description;
        this.project = issue.project;
        this.assignee = issue.assignee;
        this.creator = issue.creator;
        this.type = issue.type;
        this.priority = issue.priority;
        this.state = issue.state;
    }
}
