package com.example.PaymentService.type;

import lombok.Getter;
import lombok.ToString;

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
}