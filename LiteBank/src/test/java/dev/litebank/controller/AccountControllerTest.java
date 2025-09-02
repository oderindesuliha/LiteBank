package dev.litebank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.litebank.dto.PaymentMethod;
import dev.litebank.dto.requests.CreateAccountRequest;
import dev.litebank.dto.requests.DepositRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testCanPostDeposit() throws Exception {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber("0123456789");
        depositRequest.setAmount(new BigDecimal(200000));
        depositRequest.setPaymentMethod(PaymentMethod.CARD);
        String json = mapper.writeValueAsString(depositRequest);
        String depositEndpoint = "/api/v1/account";
        mockMvc.perform(MockMvcRequestBuilders.post(depositEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());

    }

    @Test
    void testCanCreateAccount() throws Exception {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setName("Akin Chidinma");
        createAccountRequest.setUsername("behew90829@ahanim.com");
        createAccountRequest.setPassword("password");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(createAccountRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }
}