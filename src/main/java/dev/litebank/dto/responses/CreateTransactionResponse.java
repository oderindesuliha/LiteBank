package dev.litebank.dto.responses;

import dev.litebank.dto.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class CreateTransactionResponse {
    private String id;
    private TransactionType transactionType;
    private BigDecimal amount;
}
