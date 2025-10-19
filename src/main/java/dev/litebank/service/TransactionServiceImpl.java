package dev.litebank.service;

import dev.litebank.dto.requests.CreateTransactionRequest;
import dev.litebank.dto.responses.CreateTransactionResponse;
import dev.litebank.dto.responses.TransactionResponse;
import dev.litebank.model.Transaction;
import dev.litebank.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateTransactionResponse create(CreateTransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.save(createTransactionFrom(transactionRequest));
        return buildTransactionResponseFrom(transaction);
    }

    private CreateTransactionResponse buildTransactionResponseFrom(Transaction transaction) {
        return modelMapper.map(transaction, CreateTransactionResponse.class);
    }

    private Transaction createTransactionFrom(CreateTransactionRequest transactionRequest) {
        return modelMapper.map(transactionRequest, Transaction.class);
    }

    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new RuntimeException("Transaction not found"));
        return modelMapper.map(transaction, TransactionResponse.class);
    }

    @Override
    public List<TransactionResponse> getTransactionsFor(String accountNumber) {
        List<Transaction> transactions =
                transactionRepository.findTransactionByAccountNumber(accountNumber);
        System.out.println("Transactions: " + transactions);
        Type listType = new TypeToken<List<TransactionResponse>>() {}.getType();
        return modelMapper.map(transactions, listType);
    }

}