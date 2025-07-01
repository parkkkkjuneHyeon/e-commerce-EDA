package com.example.PaymentService.entity;


import com.example.PaymentService.type.PaymentMethod;
import com.example.PaymentService.type.PaymentStatus;
import com.example.PaymentService.type.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;


@Entity
@Table(name="payments", indexes = {
        @Index(name = "idx_payment_key", columnList = "paymentKey")
})
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @Column(unique = true, nullable = false, length = 64)
    private String orderId;

    @Column(unique=true, nullable=false)
    private String paymentKey;

    @Column(nullable=false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable=false)
    private String currency;

    @Column(nullable=false)
    private Long totalAmount;

    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private ZonedDateTime approvedAt;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    public void updatePaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public void updatePaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

}
