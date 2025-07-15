package com.example.PaymentService.service;

import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.usecase.PaymentUsecaseService;
import com.google.protobuf.InvalidProtocolBufferException;
import edaordersystem.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {
    private static final Logger log = LoggerFactory.getLogger(PaymentEventListener.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final PaymentUsecaseService paymentUsecaseService;

    @KafkaListener(topics = "payment-confirm-request")
    public void paymentConfirmRequestEventListener(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.paymentRequestV1.parseFrom(message);
        log.info(
                "paymentConfirmRequestEventListener paymentRequestV1.amount : {}",
                object.getAmount()
        );

        var paymentRequestDTO = PaymentRequestDTO.builder()
                .orderId(object.getOrderId())
                .paymentKey(object.getPaymentKey())
                .amount(object.getAmount())
                .build();

        var paymentEntity = paymentUsecaseService.confirmPayment(paymentRequestDTO);

        var paymentConfirmResponseMessage = EdaMessage.paymentResponseV1
                .newBuilder()
                .setOrderId(paymentEntity.getOrderId())
                .setOrderName(paymentEntity.getOrderName())
                .setPaymentKey(paymentEntity.getPaymentKey())
                .setAmount(paymentRequestDTO.getAmount())
                .setApprovedAt(paymentEntity.getApprovedAt().toString())
                .putAllMetadata(paymentEntity.getMetadata())
                .build();

        kafkaTemplate.send(
                "payment-confirm-response",
                paymentConfirmResponseMessage.toByteArray()
        );

        log.info("payment-confirm-request -> payment-confirm-response ");
    }
}
