package com.phiworks.OrderService.feignclien;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "paymentClient", url = "http://payment-service:8080")
public class PaymentFeignClient {
}
