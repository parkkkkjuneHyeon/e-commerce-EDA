package com.phiworks.OrderService.dto.orders;

import com.phiworks.OrderService.model.entity.OrdersEntity;
import com.phiworks.OrderService.types.OrderStatus;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private String orderId;

    private Long deliveryId;

    private Long memberId;

    private String orderName;

    private Long amount;

    private OrderStatus orderStatus;    // 주문 상태

    private String paymentKey;          // 결제 완료 후 받는 키

    private String address;

    public static OrdersEntity of(OrderRequestDTO requestDTO) {
        return OrdersEntity.builder()
                .id(requestDTO.getOrderId())
                .orderName(requestDTO.getOrderName())
                .amount(requestDTO.getAmount())
                .memberId(requestDTO.getMemberId())
                .orderStatus(requestDTO.getOrderStatus())
                .paymentKey(requestDTO.getPaymentKey())
                .deliveryId(requestDTO.getDeliveryId())
                .build();
    }
}
