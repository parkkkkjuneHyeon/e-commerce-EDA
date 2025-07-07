package com.phiworks.works.deliveryservice.dto.delivery;

import com.phiworks.works.deliveryservice.entity.DeliveryEntity;
import com.phiworks.works.deliveryservice.type.DeliveryStatus;
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

    private DeliveryStatus deliveryStatus;

    private String deliveryCompany;

    public Long referenceCode;

    public static DeliveryResponseDTO of(DeliveryEntity entity) {
        return DeliveryResponseDTO.builder()
                .deliveryId(entity.getId())
                .orderId(entity.getOrderId())
                .productName(entity.getProductName())
                .productCount(entity.getProductCount())
                .address(entity.getAddress())
                .deliveryStatus(entity.getDeliveryStatus())
                .deliveryCompany(entity.getDeliveryCompany())
                .referenceCode(entity.getReferenceCode())
                .build();
    }
}
