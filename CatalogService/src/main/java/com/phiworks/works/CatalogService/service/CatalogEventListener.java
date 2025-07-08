package com.phiworks.works.CatalogService.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.phiworks.works.CatalogService.dto.StockCountDTO;
import edaordersystem.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatalogEventListener {

    private static final Logger log = LoggerFactory.getLogger(CatalogEventListener.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final CatalogService catalogService;

    @KafkaListener(topics = "catalog-decrement-request-delivery-request")
    public void catalogDecrementRequestListener(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.stockCountRequestV1.parseFrom(message);
        long productId = object.getProductId();
        var decrementStockCountDTO = StockCountDTO.builder()
                .decrementStockCount(object.getDecrementStockCount())
                .build();

        var productDTO = catalogService.decreaseStockCount(
                productId,
                decrementStockCountDTO
        );

        var deliveryRequestMessage = EdaMessage.deliveryRequestV1.newBuilder()
                .setOrderId(object.getOrderId())
                .setOrderName(object.getOrderName())
                .setAddress(object.getAddress())
                .setDeliveryCompany(object.getDeliveryCompany())
                .setProductCount(object.getDecrementStockCount())
                .setMemberId(object.getMemberId())
                .setPaymentKey(object.getPaymentKey())
                .setApprovedAt(object.getApprovedAt())
                .setAmount(object.getAmount())
                .build();

        kafkaTemplate.send(
                "delivery-request",
                deliveryRequestMessage.toByteArray()
        );

        log.info("catalogDecrementRequestListener 저장 -> delivery-request send");
    }
}
