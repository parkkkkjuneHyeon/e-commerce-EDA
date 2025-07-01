package com.example.PaymentService.dto.cash;

import com.example.PaymentService.dto.PaymentDTO;
import lombok.*;

import java.time.ZonedDateTime;

/**
 * 현금영수증 발행/취소 이력
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CashReceiptHistoryDTO {
    private String receiptKey;
    private String orderId;
    private String orderName;
    private String type;
    private String issueNumber;
    private String receiptUrl;
    private String businessNumber;
    private String transactionType;
    private Integer amount;
    private Integer taxFreeAmount;
    private String issueStatus;
    private PaymentDTO.Failure failure;
    private String customerIdentityNumber;
    private ZonedDateTime requestedAt;
}
