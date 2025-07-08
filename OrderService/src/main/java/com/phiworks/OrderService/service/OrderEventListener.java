package com.phiworks.OrderService.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.phiworks.OrderService.model.entity.OrdersEntity;
import com.phiworks.OrderService.model.repository.OrderRepository;
import com.phiworks.OrderService.types.OrderStatus;
import edaordersystem.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "payment-confirm-response")
    public void paymentConfirmResponseEventListener(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.paymentResponseV1.parseFrom(message);

        var metadataMap = object.getMetadataMap();
        String address = metadataMap.get("address");
        long productId = Long.parseLong(metadataMap.get("productId").toString());
        long productCount = Long.parseLong(metadataMap.get("productCount").toString());
        long memberId = Long.parseLong(metadataMap.get("memberId").toString());
        String deliveryCompany = metadataMap.get("deliveryCompany").toString();

        //catalog 상품 수량 업데이트
        var catalogDecrementMessage = EdaMessage.stockCountRequestV1.newBuilder()
                .setProductId(productId)
                .setDecrementStockCount(productCount)
                .setOrderId(object.getOrderId())
                .setOrderName(object.getOrderName())
                .setMemberId(memberId)
                .setDeliveryCompany(deliveryCompany)
                .setPaymentKey(object.getPaymentKey())
                .setAddress(address)
                .setAmount(object.getAmount())
                .setApprovedAt(object.getApprovedAt())
                .build();

        kafkaTemplate.send(
                "catalog-decrement-request-delivery-request",
                catalogDecrementMessage.toByteArray()
        );

        log.info("paymentConfirmResponseEventListener : 완료 {} ", object.getAmount());
    }

    @KafkaListener(topics = "order-complete")
    public void orderCompleteEventListener(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.deliveryResponseV1.parseFrom(message);

        var orderEntity = OrdersEntity.builder()
                .id(object.getOrderId())
                .orderName(object.getOrderName())
                .paymentKey(object.getPaymentKey())
                .amount(object.getAmount())
                .productCount(object.getProductCount())
                .memberId(object.getMemberId())
                .deliveryId(object.getDeliveryId())
                .createdAt(ZonedDateTime.parse(object.getApprovedAt()))
                .orderStatus(
                        OrderStatus.getOrderStatus(object.getDeliveryStatus())
                ).build();

        var savedOrderEntity = orderRepository.save(orderEntity);

        log.info("orderCompleteEventListener : {} 저장 완료", savedOrderEntity);
    }

    @KafkaListener(topics = "delivery-status-update")
    public void deliveryStatusEventListener(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.deliveryResponseV1.parseFrom(message);

        var orderEntity = orderRepository.findById(object.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("not found order"));

        orderEntity.setOrderStatus(
                OrderStatus.getOrderStatus(object.getDeliveryStatus())
        );

        var updateOrderEntity = orderRepository.save(orderEntity);

        log.info("updateOrderEntity orderId : {} orderstatus : {}",
                updateOrderEntity.getId(),
                updateOrderEntity.getOrderStatus()
        );
    }
}
