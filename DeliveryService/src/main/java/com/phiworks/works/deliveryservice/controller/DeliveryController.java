package com.phiworks.works.deliveryservice.controller;


import com.phiworks.works.deliveryservice.dto.delivery.DeliveryRequestDTO;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryResponseDTO;
import com.phiworks.works.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;


    @PostMapping("/addresses")
    public ResponseEntity<DeliveryResponseDTO> registerAddress(@RequestBody DeliveryRequestDTO deliveryRequestDTO) {


    }

    @PostMapping("/process-delivery")
    public ResponseEntity<DeliveryResponseDTO> processDelivery(@RequestBody DeliveryRequestDTO deliveryRequestDTO) {

    }


    @GetMapping("/deliveries/{deliveryId}")
    public ResponseEntity<DeliveryResponseDTO> findDeliveries(@PathVariable Long deliveryId) {

    }

    @GetMapping("/delivery/address/{addressId}")
    public ResponseEntity<DeliveryResponseDTO> findDelivery(@PathVariable Long addressId) {

    }

    @GetMapping("/members/{memberId}/first-address")
    public ResponseEntity<DeliveryResponseDTO> findFirstAddress(@PathVariable Long memberId) {

    }
}
