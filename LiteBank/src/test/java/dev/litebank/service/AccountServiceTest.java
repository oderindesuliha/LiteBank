package dev.litebank.service;


import dev.litebank.dto.requests.DepositRequest;
import dev.litebank.dto.responses.DepositResponse;
import dev.litebank.dto.responses.ViewAccountResponse;
import dev.litebank.dto.PaymentMethod;
import dev.litebank.dto.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
@Slf4j
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;


    @Test
    void testCanDeposit() throws IOException {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setPaymentMethod(PaymentMethod.CARD);
        depositRequest.setAccountNumber("0123456789");
        depositRequest.setAmount(new BigDecimal(10_000));
        DepositResponse depositResponse = accountService.deposit(depositRequest);
        assertNotNull(depositResponse);
        assertEquals(TransactionStatus.SUCCESS, depositResponse.getTransactionStatus());
    }

    @Test
    void testCanViewAccount(){
        ViewAccountResponse response =
                accountService.viewDetailsFor("0123456789");
        assertThat(response).isNotNull();
        assertThat(response.getBalance()).isEqualTo(new BigDecimal("370000.00").toString());
    }

    @Test
    void testGenerateAccountNumber() {
        String accountNumber = AccountServiceImpl.generateAccountNumber();
        log.info("Generated account number: {}", accountNumber);
        assertThat(accountNumber.length())
                .isEqualTo(10);
    }
}