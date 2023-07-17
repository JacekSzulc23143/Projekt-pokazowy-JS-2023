package com.example.projektpokazowyjs2023.issues;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum IssuePriority {
    EASY, MEDIUM, SERIOUS;

    // metoda która ze stringa robi enuma
    @JsonCreator
    public static IssuePriority fromString(@JsonProperty("priority") String value) {
        return IssuePriority.valueOf(value);
    }
}
