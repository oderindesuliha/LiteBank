package dev.litebank.security.config;

import dev.litebank.security.filter.LiteBankAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatchers;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    private LiteBankAuthenticationFilter authenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(c ->c.disable())
                .cors(Customizer.withDefaults())
                .addFilterAt(authenticationFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(c -> c.requestMatchers(HttpMethod.POST
                        .authorizeRequest()
                        .build();
    }

    @Bean
    public UserDetailsService userDetailsService(@Autowired DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    public static void main(){

    }

}
