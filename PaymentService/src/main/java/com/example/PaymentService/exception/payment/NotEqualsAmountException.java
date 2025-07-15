package com.example.PaymentService.exception.payment;


import com.example.PaymentService.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NotEqualsAmountException extends CustomException {

    public NotEqualsAmountException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
