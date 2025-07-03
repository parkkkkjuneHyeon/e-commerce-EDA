package com.phiworks.OrderService.model.repository;

import com.phiworks.OrderService.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {


    List<OrderEntity> findByMemberId(Long memberId);
}
