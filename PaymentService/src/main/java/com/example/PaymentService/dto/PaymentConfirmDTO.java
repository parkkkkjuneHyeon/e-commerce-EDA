package com.example.PaymentService.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfirmDTO {
    private String orderId;
    private String paymentKey;
    private Long amount;
}
