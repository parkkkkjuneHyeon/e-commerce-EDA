package com.phiworks.works.deliveryservice.dg.adapter;

import com.phiworks.works.deliveryservice.dg.enums.DeliveryFields;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryRequestDTO;
import org.springframework.stereotype.Component;

@Component
public abstract class DeliveryAdapter {
    private final DeliveryFields delivery;

    protected DeliveryAdapter(DeliveryFields delivery) {
        this.delivery = delivery;
    }

    public abstract Long processDelivery(DeliveryRequestDTO deliveryRequestDTO);



    public DeliveryFields getDeliveryFields() {
        return delivery;
    }

}

