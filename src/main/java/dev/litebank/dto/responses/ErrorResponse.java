package dev.litebank.dto.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse <T> {
    private T error;

    public ErrorResponse(T error) {
        this.error = error;

    }
}