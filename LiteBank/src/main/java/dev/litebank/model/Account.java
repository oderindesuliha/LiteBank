package dev.litebank.model;

import dev.litebank.dto.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String username;
    private String password;
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
