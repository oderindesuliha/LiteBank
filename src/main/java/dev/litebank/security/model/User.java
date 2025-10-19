package dev.litebank.security.model;

import dev.litebank.dto.responses.AccountResponse;
import dev.litebank.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class User implements UserDetails {

    private AccountResponse accountResponse;

//    public static void main(String[] args) {
//        System.out.println(Base64.getEncoder()
//                .encodeToString("This is a very secure jwt secret key".getBytes()));
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = accountResponse.getAuthorities()   //[ACCOUNT, ADMIN]
                .stream()
                .map((authority )->
                        new SimpleGrantedAuthority(authority.name()))//[ACCOUNT, ADMIN] -> [GrantedAuthority(ACCOUNT), grantedAuthority(ADMIN)]
                .toList();
        return authorities;
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