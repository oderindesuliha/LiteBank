package dev.litebank.dto.responses;

import dev.litebank.dto.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DepositResponse {
    private String transactionId;
    private TransactionStatus transactionStatus;
    private BigDecimal amount;
}
