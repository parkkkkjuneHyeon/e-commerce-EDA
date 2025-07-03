package com.phiworks.OrderService.model.entity;

import com.phiworks.OrderService.types.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "order")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String orderName;

    private Long count;

    private Long orderPrice;

    private Long memberId;

    private OrderStatus orderStatus;

    private String paymentKey;

    private Long deliveryId;

    private ZonedDateTime createdAt;

}
