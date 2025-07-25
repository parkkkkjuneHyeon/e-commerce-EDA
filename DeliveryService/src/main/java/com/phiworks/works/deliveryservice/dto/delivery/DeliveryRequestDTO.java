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
public class DeliveryRequestDTO {

    private String orderId;

    private String productName;

    private Long productCount;

    private String address;

    public Long referenceCode;

    private String deliveryCompany; //배달 업체

    public static DeliveryEntity of(DeliveryRequestDTO dto) {
        return DeliveryEntity.builder()
                .orderId(dto.getOrderId())
                .productName(dto.getProductName())
                .productCount(dto.getProductCount())
                .address(dto.getAddress())
                .deliveryCompany(dto.getDeliveryCompany())
                .referenceCode(dto.getReferenceCode())
                .build();
    }
    public static DeliveryEntity of(DeliveryRequestDTO dto, DeliveryStatus deliveryStatus) {
        return DeliveryEntity.builder()
                .orderId(dto.getOrderId())
                .productName(dto.getProductName())
                .productCount(dto.getProductCount())
                .address(dto.getAddress())
                .deliveryStatus(deliveryStatus)
                .deliveryCompany(dto.getDeliveryCompany())
                .referenceCode(dto.getReferenceCode())
                .build();
    }
}
