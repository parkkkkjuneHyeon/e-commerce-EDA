package com.phiworks.works.deliveryservice.dg.adapter.hanjin;

import com.phiworks.works.deliveryservice.dg.adapter.DeliveryAdapter;
import com.phiworks.works.deliveryservice.dg.enums.DeliveryFields;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class HanjinDeliveryAdapter extends DeliveryAdapter {

    protected HanjinDeliveryAdapter() {
        super(DeliveryFields.HANJIN);
    }

    @Override
    public Long processDelivery(DeliveryRequestDTO deliveryRequestDTO) {
        return 2L;
    }
}
