package com.example.projektpokazowyjs2023.projects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data // zastępuje kilka adnotacji jedną np. Getter, Setter
@Entity // adnotacja: hibernate automatycznie utworzy tabelkę Project
@EntityListeners(AuditingEntityListener.class)
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
    @Column(columnDefinition = "TEXT") // dowolna liczba znaków tekstowych w polu tekstowym
    private String description;

    @CreatedDate
    @Column(updatable = false)
    private Date dateCreated;

    @LastModifiedDate
    @Column
    private Date lastUpdated;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column
    private String lastUpdatedBy;

    @Column(nullable = false)
    private Boolean enabled = true; // nowy projekt domyślnie jest włączony
}
