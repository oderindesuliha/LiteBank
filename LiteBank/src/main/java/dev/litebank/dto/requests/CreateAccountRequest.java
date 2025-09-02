package dev.litebank.dto.requests;

import dev.litebank.dto.AccountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {
    private String username;
    private String password;
    private String name;
    private AccountType accountType;
}