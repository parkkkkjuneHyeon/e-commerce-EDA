package com.phiworks.works.deliveryservice.dg.adapter.lotte;



import com.phiworks.works.deliveryservice.dg.adapter.DeliveryAdapter;
import com.phiworks.works.deliveryservice.dg.enums.DeliveryFields;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class LotteDeliveryAdapter extends DeliveryAdapter {

    protected LotteDeliveryAdapter() {
        super(DeliveryFields.LOTTE);
    }

    @Override
    public Long processDelivery(DeliveryRequestDTO deliveryRequestDTO) {

        return 0L;
    }
}
