package dev.litebank.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountResponse {
    private String id;
    private String username;
    private String name;
    private String accountNumber;
}