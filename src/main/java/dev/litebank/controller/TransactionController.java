package dev.litebank.controller;

import dev.litebank.dto.responses.ErrorResponse;
import dev.litebank.dto.responses.TransactionResponse;
import dev.litebank.exception.AccountNotFoundException;
import dev.litebank.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(path = "/{account_number}")
    public ResponseEntity<?> getTransactionsFor(@PathVariable(name = "account_number") String accountNumber) {
        try{
            List<TransactionResponse> transactionResponses = transactionService
                    .getTransactionsFor(accountNumber);
            return ResponseEntity.status(HttpStatus.OK).body(transactionResponses);
        }catch(AccountNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse<>(e.getMessage()));
        }
    }
}