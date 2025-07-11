package com.example.PaymentService.type;

import com.example.PaymentService.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 결제수단 Enum
 */
@Getter
public enum PaymentMethod {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    EASY_PAY("간편결제"),
    MOBILE_PHONE("휴대폰"),
    TRANSFER("계좌이체"),
    CULTURE_GIFT_CERTIFICATE("문화상품권"),
    BOOK_CULTURE_GIFT_CERTIFICATE("도서문화상품권"),
    GAME_CULTURE_GIFT_CERTIFICATE("게임문화상품권");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public static PaymentMethod getPaymentMethod(String description) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.description.equals(description.toUpperCase())) {
                return paymentMethod;
            }
        }
        throw new CustomException(
                "결제종류를 잘못 입력하셨습니다. : " + description,
                HttpStatus.BAD_REQUEST
        );
    }
}
