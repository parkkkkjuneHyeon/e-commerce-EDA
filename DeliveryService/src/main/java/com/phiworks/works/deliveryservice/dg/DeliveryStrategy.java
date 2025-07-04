package com.phiworks.works.deliveryservice.dg;

import com.phiworks.works.deliveryservice.dg.adapter.DeliveryAdapter;
import com.phiworks.works.deliveryservice.dg.enums.DeliveryFields;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryRequestDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DeliveryStrategy {
    Map<DeliveryFields, DeliveryAdapter> deliveryAdaptersMap;

    public DeliveryStrategy(List<DeliveryAdapter> deliveryAdapters) {
        deliveryAdaptersMap = new HashMap<>();
        for (DeliveryAdapter deliveryAdapter : deliveryAdapters) {
            deliveryAdaptersMap.put(
                    deliveryAdapter.getDeliveryFields(),
                    deliveryAdapter
            );
        }
    }
    public Long processDelivery(
            DeliveryRequestDTO deliveryRequestDTO) {

        String deliveryCompany = deliveryRequestDTO.getDeliveryCompany();
        DeliveryAdapter deliveryAdapter = getDeliveryAdapter(deliveryCompany);

        if (deliveryAdapter == null)
            throw new IllegalArgumentException("No delivery adapter found for name " + deliveryCompany);

        return deliveryAdapter.processDelivery(deliveryRequestDTO);
    }

    private DeliveryAdapter getDeliveryAdapter(String deliveryName) {
        DeliveryFields delivery = DeliveryFields.getDeliveryFields(deliveryName);
        return deliveryAdaptersMap.get(delivery);
    }
}
