package com.phiworks.OrderService.dto.delivery;


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

    private String deliveryCompany;


}
