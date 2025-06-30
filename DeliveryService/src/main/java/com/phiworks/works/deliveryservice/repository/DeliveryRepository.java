package com.phiworks.works.deliveryservice.repository;

import com.phiworks.works.deliveryservice.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    List<DeliveryEntity> findAllByOrderId(String orderId);
}
