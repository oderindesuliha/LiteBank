package dev.litebank.dto.responses;

import dev.litebank.model.Authority;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AccountResponse {
    private String username;
    private String password;
    private  String accountNumber;
    private Set<Authority> authorities;
}