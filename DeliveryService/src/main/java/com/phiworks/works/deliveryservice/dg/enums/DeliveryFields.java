package com.phiworks.works.deliveryservice.dg.enums;

public enum DeliveryFields {
    LOTTE,
    HANJIN;


    public static DeliveryFields getDeliveryFields(String deliveryName) {
        try {
            return DeliveryFields.valueOf(deliveryName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
