package com.phiworks.OrderService.feignclien;

import com.phiworks.OrderService.dto.adress.MemberAddressResponseDTO;
import com.phiworks.OrderService.dto.delivery.DeliveryRequestDTO;
import com.phiworks.OrderService.dto.delivery.DeliveryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deliveryClient", url = "http://delivery-service:8080/delivery")
public interface DeliveryFeignClient {



    @GetMapping("/members/{memberId}/addresses")
    List<MemberAddressResponseDTO> getMemberAddress(
            @PathVariable Long memberId
    );

    @GetMapping("/deliveries/{deliveryId}")
    DeliveryResponseDTO findDelivery(@PathVariable Long deliveryId);

    @PostMapping("/process-delivery")
    DeliveryResponseDTO processDelivery(@RequestBody DeliveryRequestDTO deliveryRequestDTO);


}
