package dev.litebank.security.model;

import dev.litebank.dto.responses.AccountResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class User implements UserDetails {

    private AccountResponse accountResponse;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO: Remove hardcoded authority
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return accountResponse.getPassword();
    }

    @Override
    public String getUsername() {
        return accountResponse.getUsername();
    }
}