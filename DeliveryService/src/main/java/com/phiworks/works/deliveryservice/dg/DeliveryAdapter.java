package com.phiworks.works.deliveryservice.dg;

import com.phiworks.works.deliveryservice.dto.delivery.DeliveryRequestDTO;
import org.springframework.stereotype.Component;

@Component
public interface DeliveryAdapter {

    Long processDelivery(DeliveryRequestDTO deliveryRequestDTO);
}

