package com.phiworks.OrderService.controller;

import com.phiworks.OrderService.dto.orders.OrderRequestDTO;
import com.phiworks.OrderService.dto.orders.OrderResponseDTO;
import com.phiworks.OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/finish-order")
    public ResponseEntity<OrderResponseDTO> finishOrder(
            @RequestBody OrderRequestDTO requestDTO
    ) {
        var orderResponseDTO = orderService.finishOrder(requestDTO);

        return ResponseEntity.ok(orderResponseDTO);
    }

    @GetMapping("/users/{memberId}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrders(
            @PathVariable Long memberId
    ) {
        var orderResponseDTOList = orderService.findAllByMemberId(memberId);

        return ResponseEntity.ok(orderResponseDTOList);
    }

    @GetMapping("/orders/order/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(
            @PathVariable String orderId
    ) {
        var orderResponseDTO = orderService.findById(orderId);

        return ResponseEntity.ok(orderResponseDTO);
    }

}
