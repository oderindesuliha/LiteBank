package dev.litebank.controller;


import dev.litebank.dto.requests.CreateAccountRequest;
import dev.litebank.dto.requests.DepositRequest;
import dev.litebank.dto.responses.CreateAccountResponse;
import dev.litebank.dto.responses.DepositResponse;
import dev.litebank.dto.responses.ErrorResponse;
import dev.litebank.exception.AccountNotFoundException;
import dev.litebank.exception.UsernameAlreadyTakenException;
import dev.litebank.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody @Valid DepositRequest depositRequest){
        try {
            DepositResponse depositResponse = accountService.deposit(depositRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(depositResponse);

        }catch (AccountNotFoundException | IOException e){
//            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse<>(e.getMessage()));
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        try {
            CreateAccountResponse response = accountService.createAccount(request);
            System.out.println(">>> Returning to client: " + response);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (UsernameAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse<>(e.getMessage()));
        }
    }

}