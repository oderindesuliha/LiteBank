package dev.litebank.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {
    private String username;
    private String password;
    private String name;
}