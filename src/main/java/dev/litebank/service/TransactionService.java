package dev.litebank.service;

import dev.litebank.dto.requests.CreateTransactionRequest;
import dev.litebank.dto.responses.CreateTransactionResponse;
import dev.litebank.dto.responses.TransactionResponse;

import java.util.List;

public interface TransactionService {
    CreateTransactionResponse create(CreateTransactionRequest transactionRequest);

    TransactionResponse getTransactionById(String id);

    List<TransactionResponse> getTransactionsFor(String accountNumber);
}
