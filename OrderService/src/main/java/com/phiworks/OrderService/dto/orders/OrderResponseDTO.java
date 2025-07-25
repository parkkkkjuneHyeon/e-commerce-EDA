package com.phiworks.OrderService.dto.orders;


import com.phiworks.OrderService.model.entity.OrdersEntity;
import com.phiworks.OrderService.types.OrderStatus;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private String id;

    private Long deliveryId;

    private Long memberId;

    private String orderName;

    private Long productCount;

    private Long amount;

    private OrderStatus orderStatus;

    private String paymentStatus;

    private String deliveryStatus;

    private String paymentKey;

    private ZonedDateTime createdAt;


    public static OrderResponseDTO of(OrdersEntity entity) {
        return OrderResponseDTO.builder()
                .id(entity.getId())
                .deliveryId(entity.getDeliveryId())
                .memberId(entity.getMemberId())
                .orderName(entity.getOrderName())
                .productCount(entity.getProductCount())
                .amount(entity.getAmount())
                .orderStatus(entity.getOrderStatus())
                .paymentKey(entity.getPaymentKey())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
