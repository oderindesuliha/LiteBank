package dev.litebank.dto.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse <T>{
    private T response;

    public ErrorResponse(T response) {
        this.response = response;
    }
}
