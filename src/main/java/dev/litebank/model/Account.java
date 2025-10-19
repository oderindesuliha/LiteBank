package dev.litebank.model;

import dev.litebank.dto.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String accountNumber;
    private Set<Authority> authorities;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public Account() {
        this.authorities = new HashSet<>();
        authorities.add(Authority.ACCOUNT);
    }
}
