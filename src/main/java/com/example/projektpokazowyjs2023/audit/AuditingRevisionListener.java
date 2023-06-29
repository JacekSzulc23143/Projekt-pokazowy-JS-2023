package com.example.projektpokazowyjs2023.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

// Nasłuchiwacz
public class AuditingRevisionListener implements RevisionListener {

    // W momencie powstania nowej rewizji metoda określi człowieka, który dokonał zmiany.
    @Override
    public void newRevision(Object revisionEntity) {

        AuditedRevisionEntity auditedRevisionEntity = (AuditedRevisionEntity) revisionEntity;

        // SecurityContextHolder-przechowuje informacje o zalogowanym użytkowniku w danej sesji.
        User actor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        auditedRevisionEntity.setActor(actor.getUsername());
    }
}
