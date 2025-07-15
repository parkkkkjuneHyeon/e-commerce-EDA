package com.example.PaymentService.exception.payment;


import com.example.PaymentService.exception.CustomException;
import org.springframework.http.HttpStatus;

public class FailSavedPaymentException extends CustomException {

    public FailSavedPaymentException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
