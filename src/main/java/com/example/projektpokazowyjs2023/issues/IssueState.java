package com.example.projektpokazowyjs2023.issues;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum IssueState {
    OPEN, IN_PROGRESS, DONE;

    // metoda która ze stringa robi enuma
    @JsonCreator
    public static IssueState fromString(@JsonProperty("state") String value) {
        return IssueState.valueOf(value);
    }
}
