package com.example.PaymentService.type;

import com.example.PaymentService.exception.CustomException;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * 결제 타입 Enum
 */

@Getter
@ToString
public enum PaymentType {
    NORMAL("일반결제"),
    BILLING("자동결제"),
    BRANDPAY("브랜드페이");

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }

    public static PaymentType getPaymentType(String description) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.name().equals(description.toUpperCase())) {
                return paymentType;
            }
        }
        throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("결제방식을 잘못 입력하셨습니다. : " + description)
                .build();
    }
}