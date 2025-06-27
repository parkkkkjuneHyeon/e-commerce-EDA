package com.example.PaymentService.type;

import lombok.Getter;
import lombok.ToString;

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
}