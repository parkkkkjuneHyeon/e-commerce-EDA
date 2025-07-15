package com.phiworks.works.deliveryservice.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryRequestDTO;
import edaordersystem.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryEventListener {

    private static final Logger log = LoggerFactory.getLogger(DeliveryEventListener.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final DeliveryService deliveryService;

    @KafkaListener(topics = "delivery-request")
    public void deliveryRequestListener(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.deliveryRequestV1.parseFrom(message);

        var delivery = DeliveryRequestDTO.builder()
                .orderId(object.getOrderId())
                .deliveryCompany(object.getDeliveryCompany())
                .address(object.getAddress())
                .productCount(object.getProductCount())
                .productName(object.getOrderName())
                .build();

        var deliveryDTO = deliveryService.processDelivery(delivery);

        var deliveryResponseMessage = EdaMessage.deliveryResponseV1
                .newBuilder()
                .setOrderId(object.getOrderId())
                .setDeliveryId(deliveryDTO.getDeliveryId())
                .setMemberId(object.getMemberId())
                .setProductCount(object.getProductCount())
                .setAmount(object.getAmount())
                .setOrderName(object.getOrderName())
                .setPaymentKey(object.getPaymentKey())
                .setApprovedAt(object.getApprovedAt())
                .setDeliveryStatus(deliveryDTO.getDeliveryStatus().name())
                .build();

        kafkaTemplate.send(
                "order-complete",
                deliveryResponseMessage.toByteArray()
        );

        log.info("deliveryRequestListener -> order-complete");
    }


}
