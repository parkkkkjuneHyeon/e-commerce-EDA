package com.example.PaymentService.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@Builder
public class CustomException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
