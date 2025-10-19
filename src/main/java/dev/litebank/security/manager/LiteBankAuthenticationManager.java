package dev.litebank.security.manager;

import dev.litebank.security.exception.AuthenticationNotSupportedException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LiteBankAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authenticationProvider.supports(authentication.getClass())) {
            Authentication authResult = authenticationProvider.authenticate(authentication);
            return authResult;
        }
        throw new AuthenticationNotSupportedException("Authentication not supported by system");
    }
}
