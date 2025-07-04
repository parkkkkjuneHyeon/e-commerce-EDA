package com.phiworks.OrderService.dto.orders;


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
}
