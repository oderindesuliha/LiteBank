package dev.litebank.service;

import dev.litebank.dto.requests.DepositRequest;
import dev.litebank.dto.requests.CreateAccountRequest;
import dev.litebank.dto.responses.AccountResponse;
import dev.litebank.dto.responses.CreateAccountResponse;
import dev.litebank.dto.responses.DepositResponse;
import dev.litebank.dto.responses.ViewAccountResponse;

import java.io.IOException;

public interface AccountService {

    DepositResponse deposit(DepositRequest depositRequest) throws IOException;

    ViewAccountResponse viewDetailsFor(String number);

    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);

    AccountResponse getByUsername(String username);
}