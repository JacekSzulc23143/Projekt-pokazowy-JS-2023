package com.example.projektpokazowyjs2023.audit;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@RevisionEntity(AuditingRevisionListener.class)
@Entity
// Klasa, która przechowuje informacje o obiekcie-osobie reprezentującej rewizję
public class AuditedRevisionEntity extends DefaultRevisionEntity {

    @Getter
    @Setter
    private String actor;

}
