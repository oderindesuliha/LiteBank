package dev.litebank.service;

import dev.litebank.dto.TransactionStatus;
import dev.litebank.dto.TransactionType;
import dev.litebank.dto.requests.EmailNotificationRequest;
import dev.litebank.dto.requests.CreateTransactionRequest;
import dev.litebank.dto.requests.DepositRequest;
import dev.litebank.dto.requests.CreateAccountRequest;
import dev.litebank.dto.responses.*;
import dev.litebank.exception.UsernameAlreadyTakenException;
import dev.litebank.model.Account;
import dev.litebank.repository.AccountRepository;
import dev.litebank.exception.AccountNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;


    @Override
    public DepositResponse deposit(DepositRequest depositRequest) throws IOException {
        Account account = accountRepository.findByAccountNumber(depositRequest.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("account not found"));

        //TODO: create transaction record
        CreateTransactionRequest createTransactionRequest = getCreateTransactionRequest(depositRequest);

        var transactionResponse = transactionService.create(createTransactionRequest);
        EmailNotificationRequest request = new EmailNotificationRequest();
        request.setSubject("CR");
        request.setRecipient(account.getUsername());
        request.setMailBody(String.format("Your account %s has been credited with %s on %s",
                account.getAccountNumber(), depositRequest.getAmount(), LocalDateTime.now()));
        notificationService.notifyBy(request);
        return buildDepositResponse(transactionResponse);

    }

    @Override
    public ViewAccountResponse viewDetailsFor(String accountNumber) {
        List<TransactionResponse> transactions =
                transactionService.getTransactionsFor(accountNumber);
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmount(ZERO.toString());
        TransactionResponse response = transactions.stream()
                .reduce(transactionResponse,(a,b)->
                        computeAccountBalanceFrom(a, b, transactionResponse));

        ViewAccountResponse viewAccountResponse = new ViewAccountResponse();
        viewAccountResponse.setBalance(response.getAmount());
        return viewAccountResponse;
    }

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        if (accountRepository.findByUsername(createAccountRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException("Username already taken");
        }

        Account account = new Account();
        account.setName(normalizeName(createAccountRequest.getName()));
        account.setUsername(createAccountRequest.getUsername());

        account.setPassword(passwordEncoder.encode(createAccountRequest.getPassword()));

        account.setAccountType(createAccountRequest.getAccountType());

        String accountNumber;
        do {
            accountNumber = generateAccountNumber();
        } while (accountRepository.findByAccountNumber(accountNumber).isPresent());

        account.setAccountNumber(accountNumber);

        Account saved = accountRepository.save(account);

        return buildCreateAccountResponse(saved, account);
    }

    @Override
    public AccountResponse getByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AccountNotFoundException("account not found"));
        return modelMapper.map(account, AccountResponse.class);
    }

    private CreateAccountResponse buildCreateAccountResponse(Account saved, Account account) {
        CreateAccountResponse response = new CreateAccountResponse();
        response.setAccountNumber(saved.getAccountNumber());
        response.setAccountHolderName(toTitleCase(saved.getName()));
        response.setAccountType(saved.getAccountType());
        response.setId(account.getId());
        return response;
    }


    private static TransactionResponse computeAccountBalanceFrom(TransactionResponse a, TransactionResponse b, TransactionResponse transactionResponse) {
        BigDecimal total = ZERO;
        if (b.getTransactionType() == TransactionType.DEPOSIT)
            total = total.add(new BigDecimal(b.getAmount()));

        else
            total = total.subtract(new BigDecimal(b.getAmount()));
        transactionResponse.setAmount(
                new BigDecimal(a.getAmount())
                        .add(total).toString()
        );
        return transactionResponse;
    }

    private static DepositResponse buildDepositResponse(CreateTransactionResponse transactionResponse) {
        var depositResponse = new DepositResponse();
        depositResponse.setAmount(new BigDecimal(String.valueOf(transactionResponse.getAmount())));
        depositResponse.setTransactionId(transactionResponse.getId());
        depositResponse.setTransactionStatus(TransactionStatus.SUCCESS);
        return depositResponse;
    }

    private static CreateTransactionRequest getCreateTransactionRequest(DepositRequest depositRequest) {
        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
        createTransactionRequest.setAmount(depositRequest.getAmount());
        createTransactionRequest.setAccountNumber(depositRequest.getAccountNumber());
        createTransactionRequest.setTransactionType(TransactionType.CREDIT);
        return createTransactionRequest;
    }


    public ViewAccountResponse viewDetailsForAccount(String accountNumber) {
        List<TransactionResponse> transactions =
                transactionService.getTransactionsFor(accountNumber);
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmount(ZERO.toString());
        TransactionResponse response = transactions.stream()
                .reduce(transactionResponse,(a,b)-> {
                    BigDecimal total = ZERO;
                    if (b.getTransactionType() == TransactionType.DEPOSIT)
                        total = total.add(new BigDecimal(b.getAmount()));

                    else
                        total = total.subtract(new BigDecimal(b.getAmount()));
                    transactionResponse.setAmount(
                            new BigDecimal(a.getAmount())
                                    .add(total).toString()
                    );
                    return transactionResponse;
                });

        ViewAccountResponse viewAccountResponse = new ViewAccountResponse();
        viewAccountResponse.setBalance(response.getAmount());
        return viewAccountResponse;
    }


    private String generateAccountNumber() {
        Random random = new Random();
        long number = 1000000000L + (long) (random.nextDouble() * 8999999999L);
        return String.valueOf(number);
    }

    private String normalizeName(String name) {
        if (name == null || name.isBlank()) return name;
        return name.trim().toUpperCase();
    }

    private String toTitleCase(String name) {
        if (name == null) return null;
        return Arrays.stream(name.split(" "))
                .map(word -> word.isEmpty()
                        ? word
                        : word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }


}