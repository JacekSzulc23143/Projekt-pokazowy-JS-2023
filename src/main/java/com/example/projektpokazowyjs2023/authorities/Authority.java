package com.example.projektpokazowyjs2023.authorities;//package com.example.projektpokazowyjs2023.auth;

import jakarta.persistence.*;
import lombok.Data;

// Klasa reprezentująca uprawnienie
@Entity
@Data
public class Authority {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    AuthorityName authority;

    public Authority(AuthorityName authority) {
        this.authority = authority;
    }

    public Authority() {

    }

    public AuthorityName getAuthority() {
        return authority;
    }
}