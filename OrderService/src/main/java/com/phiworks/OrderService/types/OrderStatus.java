package com.phiworks.OrderService.types;

public enum OrderStatus {
    INITIATED,

    PAYMENT_COMPLETED,

    DELIVERY_REQUESTED,

    DELIVERY_IN_PROGRESS,

    DELIVERY_FAILED,

    DELIVERY_COMPLETED;


    public static OrderStatus getOrderStatus(String status) {
        return switch (status) {
            case "REQUESTED" -> OrderStatus.DELIVERY_REQUESTED;
            case "IN_DELIVERY" -> OrderStatus.DELIVERY_IN_PROGRESS;
            case "FAILED" -> OrderStatus.DELIVERY_FAILED;
            case "DONE" -> OrderStatus.DELIVERY_COMPLETED;
            default -> OrderStatus.INITIATED;
        };

    }
}
