package com.phiworks.works.deliveryservice.repository;

import com.phiworks.works.deliveryservice.dto.delivery.DeliveryResponseDTO;
import com.phiworks.works.deliveryservice.entity.DeliveryEntity;
import com.phiworks.works.deliveryservice.type.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    List<DeliveryEntity> findAllByOrderId(String orderId);

    List<DeliveryEntity> findAllByDeliveryStatus(DeliveryStatus deliveryStatus);

    Optional<DeliveryEntity> findByOrderId(String orderId);
}
