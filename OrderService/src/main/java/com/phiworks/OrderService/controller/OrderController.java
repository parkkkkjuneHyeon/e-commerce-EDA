package com.phiworks.OrderService.controller;

import com.phiworks.OrderService.dto.orders.OrderRequestDTO;
import com.phiworks.OrderService.dto.orders.OrderResponseDTO;
import com.phiworks.OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/finish-order")
    public ResponseEntity<HttpStatus> finishOrder(
            @RequestBody OrderRequestDTO requestDTO
    ) {
        orderService.finishOrder(requestDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
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
