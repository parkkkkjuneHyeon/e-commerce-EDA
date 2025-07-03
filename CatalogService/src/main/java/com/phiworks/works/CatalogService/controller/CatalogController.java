package com.phiworks.works.CatalogService.controller;

import com.phiworks.works.CatalogService.dto.StockCountDTO;
import com.phiworks.works.CatalogService.dto.ProductRequestDTO;
import com.phiworks.works.CatalogService.dto.ProductResponseDTO;
import com.phiworks.works.CatalogService.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;


    @PostMapping("/products/register")
    public ResponseEntity<ProductResponseDTO> registerProduct(
            @RequestBody ProductRequestDTO productRequestDTO
    ) {
        var productResponseDTO = catalogService.registerProduct(productRequestDTO);

        return ResponseEntity.ok(productResponseDTO);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("productId") Long productId) {
        catalogService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable("productId") Long productId) {
        var productResponseDTO = catalogService.getProductById(productId);

        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping("/sellers/{sellerId}/products")
    public ResponseEntity<List<ProductResponseDTO>> getProductsBySellerId(
            @PathVariable("sellerId") Long sellerId) {
        var productResponseDTO = catalogService.getProductBySellerId(sellerId);

        return ResponseEntity.ok(productResponseDTO);
    }

    @PostMapping("/products/{productId}/decreaseStockCount")
    public ResponseEntity<ProductResponseDTO> decreaseStockCount(
            @PathVariable("productId") Long productId,
            @RequestBody StockCountDTO stockCountDTO) {

        var productResponseDTO = catalogService.decreaseStockCount(
                productId,
                stockCountDTO
        );

        return ResponseEntity.ok(productResponseDTO);
    }

    @PostMapping("/products/{productId}/increaseStockCount")
    public ResponseEntity<ProductResponseDTO> increaseStockCount(
            @PathVariable("productId") Long productId,
            @RequestBody StockCountDTO stockCountDTO) {

        var productResponseDTO = catalogService.increaseStockCount(
                productId,
                stockCountDTO
        );

        return ResponseEntity.ok(productResponseDTO);
    }

}
