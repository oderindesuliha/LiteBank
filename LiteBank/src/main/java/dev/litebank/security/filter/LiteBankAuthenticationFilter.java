package dev.litebank.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.litebank.security.request.AuthRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStream;

@Component
@AllArgsConstructor
public class LiteBankAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       if(!request.getServletPath().equals("/login"))
           filterChain.doFilter(request, response);
        ObjectMapper mapper = new ObjectMapper();
        InputStream requestBody = request.getInputStream(); //{"username":"", "password":""}
        AuthRequest authRequest = mapper.readValue(requestBody, AuthRequest.class);
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authResult =  authenticationManager.authenticate(authentication);
    }
}

