package com.example.PaymentService.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    private Long memberId;
    private String orderId;
    private String paymentKey;
    private Long amount;
}
