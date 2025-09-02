package dev.litebank.dto.responses;

import dev.litebank.dto.TransactionType;
import lombok.Getter;
import lombok.Setter;

;

@Getter
@Setter
public class TransactionResponse {
    private String id;
    private TransactionType transactionType;
    private String amount;

}
