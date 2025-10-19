package dev.litebank.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.litebank.dto.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class DepositRequest {
    @Size(min = 10, max = 10, message = "account number must be 10 digits long")
    private String accountNumber;
    @Min(value = 50, message = "minimum deposit amount = 50")
    private BigDecimal amount;

    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod;
}