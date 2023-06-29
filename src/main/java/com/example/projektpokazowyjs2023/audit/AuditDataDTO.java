package com.example.projektpokazowyjs2023.audit;

import com.example.projektpokazowyjs2023.issues.Issue;
import com.example.projektpokazowyjs2023.issues.IssueState;
import lombok.Data;
import org.hibernate.envers.RevisionType;

import java.util.Date;

//@Getter
//@Setter
@Data
public class AuditDataDTO {

    Date date;
    String actor;
    RevisionType revisionType;
    String name;
    IssueState state;

    // Konstruktor
    public AuditDataDTO(Object[] revision) {
        AuditedRevisionEntity auditedRevisionEntity = (AuditedRevisionEntity) revision[1];

        this.date = new Date(auditedRevisionEntity.getTimestamp());
        this.actor = auditedRevisionEntity.getActor();

        this.revisionType = (RevisionType) revision[2];

        Issue issue = (Issue) revision[0];
        this.name = issue.getName();
        this.state = issue.getState();
    }
}
