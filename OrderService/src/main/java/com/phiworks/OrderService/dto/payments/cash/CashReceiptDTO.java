package com.phiworks.OrderService.dto.payments.cash;

import lombok.*;

/**
 * 현금영수증 정보
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CashReceiptDTO {
    private String type;
    private String receiptKey;
    private String issueNumber;
    private String receiptUrl;
    private Long amount;
    private Long taxFreeAmount;
}
