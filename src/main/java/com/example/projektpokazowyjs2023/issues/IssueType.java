package com.example.projektpokazowyjs2023.issues;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum IssueType {
    BUG, TASK, DOCUMENTATION;

    // metoda która ze stringa robi enuma
    @JsonCreator
    public static IssueType fromString(@JsonProperty("type") String value) {
        return IssueType.valueOf(value);
    }
}
