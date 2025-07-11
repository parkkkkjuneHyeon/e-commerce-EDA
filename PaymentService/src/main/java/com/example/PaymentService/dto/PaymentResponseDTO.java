package com.example.PaymentService.dto;

import com.example.PaymentService.entity.PaymentsEntity;
import com.example.PaymentService.type.PaymentMethod;
import com.example.PaymentService.type.PaymentStatus;
import com.example.PaymentService.type.PaymentType;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private Long memberId;
    private String orderId;
    private String paymentKey;
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
    private String currency;
    private Long totalAmount;
    private PaymentStatus paymentStatus;


    public static PaymentResponseDTO of(PaymentsEntity paymentsEntity) {
        return PaymentResponseDTO.builder()
                .id(paymentsEntity.getId())
                .memberId(paymentsEntity.getMemberId())
                .orderId(paymentsEntity.getOrderId())
                .paymentKey(paymentsEntity.getPaymentKey())
                .paymentType(paymentsEntity.getPaymentType())
                .paymentMethod(paymentsEntity.getPaymentMethod())
                .currency(paymentsEntity.getCurrency())
                .totalAmount(paymentsEntity.getTotalAmount())
                .paymentStatus(paymentsEntity.getPaymentStatus())
                .build();
    }
}
