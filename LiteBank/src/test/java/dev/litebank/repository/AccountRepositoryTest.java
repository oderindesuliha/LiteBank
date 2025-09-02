package dev.litebank.repository;

import dev.litebank.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @Sql(scripts = {"/db/data.sql"})
    void testFindByAccountNumber() {
        Optional<Account> account = accountRepository. findByAccountNumber("0123456789");
        Account customerAccount = account.orElseThrow(RuntimeException::new);
        assertNotNull(customerAccount);
        }

}