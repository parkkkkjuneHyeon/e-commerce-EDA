package com.phiworks.OrderService.feignclien;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "deliveryClient", url = "http://delivery-service:8080")
public class DeliveryFeignClient {
}
