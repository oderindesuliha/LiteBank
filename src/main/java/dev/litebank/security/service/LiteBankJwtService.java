package dev.litebank.security.service;

import com.auth0.jwt.interfaces.Claim;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LiteBankJwtService implements  JwtService {


    @Override
    public String generateAccessToken(Authentication authentication) {
        return "";
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {
        return "";
    }

    @Override
    public boolean isJwtTokenValid(String token) {
        return false;
    }

    @Override
    public Claim extractClaim(String token, String claimName) {
        return null;
    }
}