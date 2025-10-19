package dev.litebank.security.service;

import com.auth0.jwt.interfaces.Claim;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public interface JwtService {
    String generateAccessToken(Authentication authentication);

    String generateRefreshToken(Authentication authentication);

    boolean isJwtTokenValid(String token);

    Claim extractClaim(String token, String claimName);
}
