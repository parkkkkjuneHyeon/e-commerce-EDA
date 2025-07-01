package com.phiworks.works.deliveryservice.entity;


import com.phiworks.works.deliveryservice.type.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "delivery", indexes = {
        @Index(name = "idx_order_id", columnList = "orderId")
})
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    private String productName;

    private Long productCount;

    private String address;

    private Long referenceCode;

    private DeliveryStatus deliveryStatus;
}
