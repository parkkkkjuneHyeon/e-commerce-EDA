package com.phiworks.OrderService.model.entity;

import com.phiworks.OrderService.types.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrdersEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(64)")
    private String id;

    private String orderName;

    private Long productCount;          //paymentFeignClient에서 가져와서 저장

    private Long amount;

    private Long memberId;

    private OrderStatus orderStatus;

    private String paymentKey;

    private Long deliveryId;

    private ZonedDateTime createdAt;

}
