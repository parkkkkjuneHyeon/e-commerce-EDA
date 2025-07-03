package com.phiworks.OrderService.dto;


import com.phiworks.OrderService.types.OrderStatus;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private String id;

    private String orderName;

    private Long count;

    private Long orderPrice;

    private Long memberId;

    private OrderStatus orderStatus;

    private String paymentStatus;

    private String deliveryStatus;

    private String paymentKey;

    private Long deliveryId;
}
