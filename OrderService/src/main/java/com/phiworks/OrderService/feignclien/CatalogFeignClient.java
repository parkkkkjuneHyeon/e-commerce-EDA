package com.phiworks.OrderService.feignclien;

import com.phiworks.OrderService.dto.catalog.ProductResponseDTO;
import com.phiworks.OrderService.dto.catalog.StockCountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "catalogClient", url = "http://catalog-service:8080/catalog")
public interface CatalogFeignClient {


    @PostMapping("/products/{productId}/increaseStockCount")
    ProductResponseDTO increaseStockCount(
            @PathVariable("productId") Long productId,
            @RequestBody StockCountDTO stockCountDTO
    );

    @PostMapping("/products/{productId}/decreaseStockCount")
    ProductResponseDTO decreaseStockCount(
            @PathVariable("productId") Long productId,
            @RequestBody StockCountDTO stockCountDTO);
}
