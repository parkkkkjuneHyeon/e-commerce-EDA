package com.example.PaymentService.exception.payment;


import com.example.PaymentService.exception.CustomException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PaymentException extends CustomException {

    public PaymentException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
