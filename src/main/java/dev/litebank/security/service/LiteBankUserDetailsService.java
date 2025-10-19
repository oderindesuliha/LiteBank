package dev.litebank.security.service;

import dev.litebank.dto.responses.AccountResponse;
import dev.litebank.security.model.User;
import dev.litebank.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class LiteBankUserDetailsService implements UserDetailsService {

    private final AccountService  accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountResponse accountResponse = accountService.getByUsername(username);

        return new User(accountResponse);

    }
}