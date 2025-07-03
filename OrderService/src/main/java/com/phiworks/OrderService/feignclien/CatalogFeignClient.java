package com.phiworks.OrderService.feignclien;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "catalogClient", url = "http://catalog-service:8080")
public class CatalogFeignClient {
}
