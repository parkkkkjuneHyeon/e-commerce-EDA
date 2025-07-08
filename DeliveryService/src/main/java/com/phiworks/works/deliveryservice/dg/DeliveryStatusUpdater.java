package com.phiworks.works.deliveryservice.dg;


import com.phiworks.works.deliveryservice.entity.DeliveryEntity;
import com.phiworks.works.deliveryservice.repository.DeliveryRepository;
import com.phiworks.works.deliveryservice.type.DeliveryStatus;
import edaordersystem.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryStatusUpdater {
    private final DeliveryRepository deliveryRepository;

    private final KafkaTemplate<String, byte[]> kafkaTemplate;


    @Scheduled(fixedRate = 200000)
    public void deliveryStatusUpdate() {
        log.info("--------Start deliveryStatusUpdate--------");
        List<DeliveryEntity> requestStatusDeliveryEntities = deliveryRepository
                .findAllByDeliveryStatus(DeliveryStatus.REQUESTED);

        requestStatusDeliveryEntities.forEach(deliveryEntity -> {
            deliveryEntity.setDeliveryStatus(DeliveryStatus.IN_DELIVERY);

            var deliveryResponseMessage = EdaMessage.deliveryResponseV1.newBuilder()
                    .setDeliveryId(deliveryEntity.getId())
                    .setOrderId(deliveryEntity.getOrderId())
                    .setDeliveryStatus(deliveryEntity.getDeliveryStatus().name())
                    .build();

            kafkaTemplate.send(
                    "delivery-status-update",
                    deliveryResponseMessage.toByteArray()
            );

            deliveryRepository.save(deliveryEntity);
        });

        List<DeliveryEntity> inDeliveryStatusDeliveryEntities = deliveryRepository
                .findAllByDeliveryStatus(DeliveryStatus.IN_DELIVERY);

        inDeliveryStatusDeliveryEntities.forEach(deliveryEntity -> {
            deliveryEntity.setDeliveryStatus(DeliveryStatus.DONE);

            var deliveryResponseMessage = EdaMessage.deliveryResponseV1.newBuilder()
                    .setDeliveryId(deliveryEntity.getId())
                    .setOrderId(deliveryEntity.getOrderId())
                    .setDeliveryStatus(deliveryEntity.getDeliveryStatus().name())
                    .build();

            kafkaTemplate.send(
                    "delivery-status-update",
                    deliveryResponseMessage.toByteArray()
            );

            deliveryRepository.save(deliveryEntity);
        });
        log.info("--------end deliveryStatusUpdate--------");
    }
}
