package com.phiworks.works.deliveryservice.controller;


import com.phiworks.works.deliveryservice.dto.address.MemberAddressRequestDTO;
import com.phiworks.works.deliveryservice.dto.address.MemberAddressResponseDTO;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryRequestDTO;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryResponseDTO;
import com.phiworks.works.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;


    @PostMapping("/addresses/register")
    public ResponseEntity<MemberAddressResponseDTO> registerAddress(@RequestBody MemberAddressRequestDTO requestDTO) {
        return ResponseEntity.ok(deliveryService.registerAddress(requestDTO));
    }

    @PostMapping("/process-delivery")
    public ResponseEntity<DeliveryResponseDTO> processDelivery(@RequestBody DeliveryRequestDTO deliveryRequestDTO) {
        return ResponseEntity.ok(deliveryService.processDelivery(deliveryRequestDTO));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<DeliveryResponseDTO> getDelivery(@PathVariable String orderId) {
        return ResponseEntity.ok(deliveryService.getDeliveryByOrderId(orderId));
    }

    @GetMapping("/deliveries/{deliveryId}")
    public ResponseEntity<DeliveryResponseDTO> findDeliveries(@PathVariable Long deliveryId) {
        return ResponseEntity.ok(deliveryService.getDelivery(deliveryId));
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<MemberAddressResponseDTO> getAddress(@PathVariable Long addressId) {
        return ResponseEntity.ok(deliveryService.getAddress(addressId));
    }

    @GetMapping("/members/{memberId}/addresses")
    public ResponseEntity<List<MemberAddressResponseDTO>> getMemberAddress(@PathVariable Long memberId) {
        return ResponseEntity.ok(deliveryService.getMemberAddress(memberId));
    }
}
