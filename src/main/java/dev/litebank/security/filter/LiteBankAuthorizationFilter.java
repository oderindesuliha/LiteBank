package dev.litebank.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.litebank.util.ProjectUtil.PUBLIC_ENDPOINTS;


@Component
@AllArgsConstructor
public class LiteBankAuthorizationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            if (Arrays.asList(PUBLIC_ENDPOINTS)
                    .contains(request.getServletPath())) {
                filterChain.doFilter(request, response);
                return;
            }
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);//"Bearer etryjjkkslsks"
            String jwt = authorizationHeader.substring("Bearer ".length());//"eretyrurjdkd"

            Claim claim = JWT.decode(jwt)
                    .getClaim("roles");

            List<String> authorities = claim.asList(String.class);
            List<SimpleGrantedAuthority> userAuth = authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, userAuth);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getOutputStream().write(objectMapper.writeValueAsBytes(errors));
            response.flushBuffer();
        }
        filterChain.doFilter(request, response);

    }
}