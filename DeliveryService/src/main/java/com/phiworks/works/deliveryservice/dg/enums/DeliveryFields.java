package com.phiworks.works.deliveryservice.dg.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum DeliveryFields {
    LOTTE,
    HANJIN;


    public static DeliveryFields getDeliveryFields(String deliveryCompany) {
        try {
            log.info("getDeliveryFields {}", deliveryCompany);
            return DeliveryFields.valueOf(deliveryCompany.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
