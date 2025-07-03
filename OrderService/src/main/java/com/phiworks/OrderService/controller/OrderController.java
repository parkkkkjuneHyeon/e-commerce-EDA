package com.phiworks.OrderService.controller;

import com.phiworks.OrderService.dto.OrderRequestDTO;
import com.phiworks.OrderService.dto.OrderResponseDTO;
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

    @PostMapping("/start-order")
    public ResponseEntity<OrderResponseDTO> startOrder(
            @RequestBody OrderRequestDTO requestDTO
    ) {

        return ResponseEntity.ok(new OrderResponseDTO());
    }

    @PostMapping("/finish-order")
    public ResponseEntity<OrderResponseDTO> finishOrder(
            @RequestBody OrderRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(new OrderResponseDTO());
    }

    @GetMapping("/uers/{memberId}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrders(
            @PathVariable Long memberId
    ) {


        return null;
    }

    @GetMapping("/orders/order/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(
            @PathVariable String orderId
    ) {


        return null;
    }


}
