package com.phiworks.OrderService.service;

import com.phiworks.OrderService.dto.OrderRequestDTO;
import com.phiworks.OrderService.dto.OrderResponseDTO;
import com.phiworks.OrderService.model.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderResponseDTO startOrder(OrderRequestDTO requestDTO) {
        return null;
    }
}
