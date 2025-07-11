package com.example.PaymentService.type;

import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.exception.CustomException;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * 결제 상태 Enum
 */
@Getter
@ToString
public enum PaymentStatus {
    READY("결제 생성"),
    IN_PROGRESS("인증 완료"),
    WAITING_FOR_DEPOSIT("입금 대기"),
    DONE("결제 완료"),
    CANCELED("결제 취소"),
    PARTIAL_CANCELED("부분 취소"),
    ABORTED("결제 실패"),
    EXPIRED("결제 만료");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public static PaymentStatus getPaymentStatus(String paymentStatus) {
        for(var status : PaymentStatus.values()) {
            if(status.name().equals(paymentStatus.toUpperCase())) {
                return status;
            }
        }
        throw new CustomException(
                "paymentStatus를 잘못 입력하셨습니다. : " + paymentStatus,
                HttpStatus.BAD_REQUEST);
    }
}