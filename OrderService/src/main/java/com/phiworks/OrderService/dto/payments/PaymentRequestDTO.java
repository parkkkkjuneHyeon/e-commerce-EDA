package com.phiworks.OrderService.dto.payments;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    private String memberId;
    private String orderId;
    private String paymentKey;
    private Long amount;
    private String currency;
    private String cancelReason;
}
