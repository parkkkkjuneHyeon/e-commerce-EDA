package com.phiworks.OrderService.service;

import com.phiworks.OrderService.dto.orders.OrderRequestDTO;
import com.phiworks.OrderService.dto.orders.OrderResponseDTO;
import com.phiworks.OrderService.model.repository.OrderRepository;
import edaordersystem.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @Transactional
    public void finishOrder(OrderRequestDTO requestDTO) {
        // 1. 결제 시작
        var paymentConfirmMessage = EdaMessage.paymentRequestV1.newBuilder()
                .setOrderId(requestDTO.getOrderId())
                .setPaymentKey(requestDTO.getPaymentKey())
                .setAmount(requestDTO.getAmount())
                .build();

        kafkaTemplate.send(
                "payment-confirm-request",
                paymentConfirmMessage.toByteArray()
        );
    }

    public List<OrderResponseDTO> findAllByMemberId(Long memberId) {
        var orderEntityList = orderRepository.findAllByMemberId(memberId);

        return orderEntityList.stream().map(OrderResponseDTO::of).toList();
    }

    public OrderResponseDTO findById(String orderId) {
        var orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderResponseDTO.of(orderEntity);
    }


}
