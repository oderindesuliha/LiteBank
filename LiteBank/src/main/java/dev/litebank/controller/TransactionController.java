package dev.litebank.controller;

import dev.litebank.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(path = "/{account_number}")
    public ResponseEntity<?> getTransactionsFor(@PathVariable(name = "account_number") String accountNumber) {
        return ResponseEntity.ok(transactionService.getTransactionsFor(accountNumber));
    }
}