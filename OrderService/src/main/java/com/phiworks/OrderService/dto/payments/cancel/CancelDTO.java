package com.phiworks.OrderService.dto.payments.cancel;

import lombok.*;

import java.time.ZonedDateTime;


/**
 * 결제 취소 정보
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CancelDTO {
    private String transactionKey;
    private String cancelReason;
    private Long cancelAmount;
    private Long taxFreeAmount;
    private Integer taxExemptionAmount;
    private Long refundableAmount;
    private Long transferDiscountAmount;
    private Long easyPayDiscountAmount;
    private ZonedDateTime canceledAt;
    private String receiptKey;
    private String cancelStatus;
    private String cancelRequestId;
}