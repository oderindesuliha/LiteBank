package dev.litebank.service;


import dev.litebank.dto.requests.*;
import dev.litebank.dto.responses.*;
import dev.litebank.model.Account;
import dev.litebank.dto.TransactionStatus;
import dev.litebank.dto.TransactionType;
import dev.litebank.repository.AccountRepository;
import dev.litebank.exception.AccountNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.math.BigDecimal.ZERO;
import static java.time.LocalTime.now;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;

    @Override
    public CreateAccountResponse create(CreateAccountRequest createAccountRequest) {
        Account account = modelMapper.map(createAccountRequest, Account.class);
        account.setAccountNumber(generateAccountNumber());
        account = accountRepository.save(account);
        return modelMapper.map(account, CreateAccountResponse.class);
    }

    public static String generateAccountNumber() {
        return IntStream.generate(() -> new SecureRandom().nextInt(0, 10))
                .limit(10)
                .mapToObj(a -> a + "")
                .collect(Collectors.joining(""));
    }

    @Override
    public DepositResponse deposit(DepositRequest depositRequest) throws IOException {
        Account account = accountRepository.findByAccountNumber(depositRequest.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("account not found"));
        CreateTransactionRequest createTransactionRequest = buildTransactionRequest(depositRequest);
        var transactionResponse = transactionService.create(createTransactionRequest);
        EmailNotificationRequest request = buildDepositNotificationRequest(depositRequest, account);
        notificationService.notifyBy(request);
        return buildDepositResponse(transactionResponse);
    }

    private static EmailNotificationRequest buildDepositNotificationRequest(DepositRequest depositRequest, Account account) {
        EmailNotificationRequest request = new EmailNotificationRequest();
        request.setSubject("CR");
        request.setTo(List.of(new Recipient(account.getName(), account.getUsername())));
        request.setHtmlContent(String.format("Your account %s has been credited with %s on %s",
                account.getAccountNumber(), depositRequest.getAmount(), LocalDateTime.now()));
        return request;
    }

    @Override
    public ViewAccountResponse viewDetailsFor(String accountNumber) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmount(ZERO.toString());
        TransactionResponse response = transactionService.getTransactionsFor(accountNumber).stream()
                .reduce(transactionResponse, (a, b) ->
                        computeAccountBalanceFrom(a, b, transactionResponse));
        ViewAccountResponse viewAccountResponse = new ViewAccountResponse();
        viewAccountResponse.setBalance(response.getAmount());
        return viewAccountResponse;
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

    public ViewAccountResponse viewDetailsForAccount(String accountNumber) {
        List<TransactionResponse> transactions =
                transactionService.getTransactionsFor(accountNumber);
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmount(ZERO.toString());
        TransactionResponse response = transactions.stream()
                .reduce(transactionResponse, (a, b) -> {
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

    private static CreateTransactionRequest buildTransactionRequest(DepositRequest
                                                                            depositRequest) {
        var createTransactionRequest = new CreateTransactionRequest();
        createTransactionRequest.setAmount(depositRequest.getAmount());
        createTransactionRequest.setAccountNumber(depositRequest.getAccountNumber());
        createTransactionRequest.setTransactionType(TransactionType.CREDIT);
        return createTransactionRequest;
    }

    private static DepositResponse buildDepositResponse(CreateTransactionResponse
                                                                transactionResponse) {
        var depositResponse = new DepositResponse();
        depositResponse.setAmount(new BigDecimal(String.valueOf(transactionResponse.getAmount())));
        depositResponse.setTransactionId(transactionResponse.getId());
        depositResponse.setTransactionStatus(TransactionStatus.SUCCESS);
        return depositResponse;
    }
}