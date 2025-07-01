package com.phiworks.works.deliveryservice.dg;

import com.phiworks.works.deliveryservice.dg.adapter.DeliveryAdapter;
import com.phiworks.works.deliveryservice.dg.enums.DeliveryFields;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryRequestDTO;
import lombok.extern.slf4j.Slf4j;
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

        String deliveryName = deliveryRequestDTO.getDeliveryName();
        DeliveryAdapter deliveryAdapter = getDeliveryAdapter(deliveryName);

        if (deliveryAdapter == null)
            throw new IllegalArgumentException("No delivery adapter found for name " + deliveryName);

        return deliveryAdapter.processDelivery(deliveryRequestDTO);
    }

    private DeliveryAdapter getDeliveryAdapter(String deliveryName) {
        DeliveryFields delivery = DeliveryFields.getDeliveryFields(deliveryName);
        return deliveryAdaptersMap.get(delivery);
    }
}
