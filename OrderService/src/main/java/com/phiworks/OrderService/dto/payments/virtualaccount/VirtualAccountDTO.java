package com.phiworks.OrderService.dto.payments.virtualaccount;

import lombok.*;

import java.time.ZonedDateTime;

/**
 * 가상계좌 결제 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VirtualAccountDTO {
    private String accountType;
    private String accountNumber;
    private String bankCode;
    private String customerName;
    private ZonedDateTime dueDate;
    private String refundStatus;
    private Boolean expired;
    private String settlementStatus;
    private RefundReceiveAccount refundReceiveAccount;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class RefundReceiveAccount {
        private String bankCode;
        private String accountNumber;
        private String holderName;
    }
}
