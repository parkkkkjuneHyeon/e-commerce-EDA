package com.phiworks.OrderService.dto.delivery;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDTO {
    private Long deliveryId;

    private String orderId;

    private String productName;

    private Long productCount;

    private String address;

    private String deliveryStatus;

    private String deliveryCompany;

    public Long referenceCode;
}
