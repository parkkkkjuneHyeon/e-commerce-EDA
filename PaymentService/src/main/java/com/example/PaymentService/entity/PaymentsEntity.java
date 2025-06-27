package com.example.PaymentService.entity;


import com.example.PaymentService.type.PaymentStatus;
import com.example.PaymentService.type.PaymentType;
import jakarta.persistence.*;

@Entity
@Table(name="payments", indexes = {@Index(name = "idx_payment_key", columnList = "paymentKey")})
public class PaymentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String orderId;

    @Column(unique=true, nullable=false)
    private String paymentKey;

    @Column(nullable=false)
    private PaymentType paymentType;

    @Column(nullable=false)
    private String currency;

    @Column(nullable=false)
    private Long totalAmount;

    @Column(nullable = false)
    private PaymentStatus paymentStatus;

}
